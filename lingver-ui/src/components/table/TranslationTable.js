import React, {Component} from 'react';
import {Table, withStyles} from '@material-ui/core';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import Tooltip from "@material-ui/core/Tooltip";
import TablePagination from "@material-ui/core/TablePagination";
import {withSnackbar} from "notistack";
import Paper from "@material-ui/core/Paper";
import Checkbox from "@material-ui/core/Checkbox";
import ConfirmationDialog from "../ConfirmationDialog";
import TableToolbar from "./TableToolbar";
import {Trans} from "react-i18next";

const styles = {
    stickyHeader: {
        "& th": {
            position: "sticky",
            top: '64px',
            background: "#fff",
            zIndex: 10
        }
    },

};

class TranslationTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dialogIsOpen: false,
            selected: [],
            titles: [
                {displayName: <Trans>Word</Trans>, path: 'translation.word.value'},
                {displayName: <Trans>Translation</Trans>, path: 'translation.value'},
                {displayName: <Trans>Insert Date</Trans>, path: 'insertDate'},
                {displayName: <Trans>Last Repeat Date</Trans>, path: 'lastRepeatDate'},
                {displayName: <Trans>Progress</Trans>, path: 'successRepeatCount'}
            ],
        };
    }

    handleChangeSort = (field) => {
        let sortDirection = 'desc';
        if (this.props.params.sortField === field && this.props.params.sortDirection === 'desc') {
            sortDirection = 'asc';
        }
        this.props.onQueryParamsChange({sortDirection: sortDirection, sortField: field});
    };

    clearSelected = () => {
        this.setState({selected: []});
    };


    handleChangePage = (event, page) => {
        this.props.onQueryParamsChange({page: page});
    };

    handleChangeRowsPerPage = (event) => {
        this.props.onQueryParamsChange({size: event.target.value});
    };

    handleSelectAllClick = event => {
        const {selected} = this.state;
        const {translations} = this.props;
        if (event.target.checked) {
            //filter ids that already were selected
            let translationsToAdd = translations.map(profileTranslation => profileTranslation.id)
                .filter(id => !selected.includes(id));
            this.setState({selected: selected.concat(translationsToAdd)});
        } else {
            //remove current page translations ids from selected
            this.setState({
                selected: selected.filter(selectedId => !translations
                    .map(translation => translation.id).includes(selectedId))
            });
        }
    };

    handleCheckboxClick = (event, id) => {
        const {selected} = this.state;
        const selectedIndex = selected.indexOf(id);
        let newSelected = [];

        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selected, id);
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selected.slice(1));
        } else if (selectedIndex === selected.length - 1) {
            newSelected = newSelected.concat(selected.slice(0, -1));
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                selected.slice(0, selectedIndex),
                selected.slice(selectedIndex + 1),
            );
        }
        this.setState({selected: newSelected});
    };

    handleDeleteClick = () => {
        this.openDialog();
    };

    isSelected = id => this.state.selected.indexOf(id) !== -1;

    openDialog = () => {
        this.setState({dialogIsOpen: true});
    };

    closeDialog = () => {
        this.setState({dialogIsOpen: false});
    };

    performDelete = () => {
        this.props.onRemove(this.state.selected);
        this.setState({selected: []});
        this.closeDialog();
    };

    getYearFromString = (date) => {
        if (date) {
            return parseInt(date.split('.')[2]);
        }
        return null;
    };

    render() {
        const {translations, classes} = this.props;
        return (
            <React.Fragment>
                <ConfirmationDialog
                    open={this.state.dialogIsOpen}
                    onCancel={this.closeDialog}
                    onConfirm={this.performDelete}
                    title={<Trans>Confirm delete</Trans>}
                    contentText={<Trans>Are you sure to delete selected items?</Trans>}
                />
                <Paper>
                    <TableToolbar
                        className={classes.stickyToolbar}
                        onDelete={this.handleDeleteClick}
                        onQueryParamsChange={this.props.onQueryParamsChange}
                        clearSelected={this.clearSelected}
                        selected={this.state.selected}/>
                    <Table padding={"dense"}>
                        <TableHead>
                            <TableRow className={classes.stickyHeader}>
                                <TableCell>
                                    <Checkbox
                                        checked={
                                            this.state.selected.length !== 0 &&
                                            this.props.translations.map(t => t.id)
                                                .every((e) => {
                                                    return this.state.selected.includes(e);
                                                })
                                        }
                                        onChange={this.handleSelectAllClick}
                                        color="primary"
                                    />
                                </TableCell>
                                {this.state.titles.map(
                                    title => (
                                        <TableCell key={title.path}>
                                            <Tooltip
                                                title="Sort"
                                                enterDelay={300}
                                            >
                                                <TableSortLabel
                                                    active={this.props.params.sortField === title.path}
                                                    direction={this.props.params.sortDirection}
                                                    onClick={() => this.handleChangeSort(title.path)}
                                                >
                                                    {title.displayName}
                                                </TableSortLabel>
                                            </Tooltip>
                                        </TableCell>
                                    ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>{
                            translations.map(row => {
                                const isSelected = this.isSelected(row.id);
                                return (
                                    <TableRow
                                        key={row.id}
                                        selected={isSelected}>
                                        <TableCell>
                                            <Checkbox color="primary"
                                                      onClick={event => this.handleCheckboxClick(event, row.id)}
                                                      checked={isSelected}/>
                                        </TableCell>
                                        <TableCell>{row.word}</TableCell>
                                        <TableCell>{row.translation}</TableCell>
                                        <TableCell>{row.insertDate}</TableCell>
                                        <TableCell>{this.getYearFromString(row.lastRepeatDate) < 2000 ? null : row.lastRepeatDate}</TableCell>
                                        <TableCell>{row.successRepeatCount}</TableCell>
                                    </TableRow>
                                );
                            })}
                        </TableBody>
                    </Table>
                    <TablePagination
                        rowsPerPageOptions={[5, 10, 20, 30]}
                        component="div"
                        count={parseInt(this.props.totalElements)}
                        rowsPerPage={parseInt(this.props.params.size)}
                        page={parseInt(this.props.params.page)}
                        backIconButtonProps={{
                            'aria-label': 'Previous Page',
                        }}
                        nextIconButtonProps={{
                            'aria-label': 'Next Page',
                        }}
                        labelRowsPerPage={<Trans>Rows per page</Trans>}
                        onChangePage={this.handleChangePage}
                        onChangeRowsPerPage={this.handleChangeRowsPerPage}
                    />
                </Paper>
            </React.Fragment>
        );
    }
}

export default withStyles(styles)(withSnackbar(TranslationTable));