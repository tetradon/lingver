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
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from '@material-ui/icons/Delete';
import {lighten} from '@material-ui/core/styles/colorManipulator';
import classNames from 'classnames';

const styles = theme => ({
    root: {
        paddingRight: theme.spacing.unit,
    },
    highlight:
        theme.palette.type === 'light'
            ? {
                color: theme.palette.primary.main,
                backgroundColor: lighten(theme.palette.primary.light, 0.85),
            }
            : {
                color: theme.palette.text.primary,
                backgroundColor: theme.palette.primary.dark,
            },
    spacer: {
        flex: '1 1 100%',
    },
    title: {
        flex: '0 0 auto',
    },
});

class TranslationList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: [],
            titles: [
                {displayName: 'Word', path: 'translation.word.value'},
                {displayName: 'Translation', path: 'translation.value'},
                {displayName: 'Insert Date', path: 'insertDate'}
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

    handleChangePage = (event, page) => {
        this.props.onQueryParamsChange({page: page});
    };

    handleChangeRowsPerPage = (event) => {
        this.props.onQueryParamsChange({size: event.target.value});
    };

    handleSelectAllClick = event => {
        if (event.target.checked) {
            this.setState({selected: this.props.translations.map(n => n.id)});
            return;
        }
        this.setState({selected: []});
    };

    handleClick = (event, id) => {
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

    isSelected = id => this.state.selected.indexOf(id) !== -1;

    render() {
        const {translations, classes} = this.props;
        return (
            <Paper className={classes.root}>
                <Toolbar
                    className={classNames(classes.root, {
                        [classes.highlight]: this.state.selected.length > 0,
                    })}
                >
                    <div className={classes.title}>
                        {this.state.selected.length > 0 ? (
                            <Typography color="inherit" variant="subtitle1">
                                {this.state.selected.length} selected
                            </Typography>
                        ) : (
                            <Typography variant="h6" id="tableTitle">
                                Your translations
                            </Typography>
                        )}
                    </div>
                    <div className={classes.spacer}/>
                    <div className={classes.actions}>
                        {this.state.selected.length > 0 ? (
                            <Tooltip title="Delete">
                                <IconButton aria-label="Delete">
                                    <DeleteIcon/>
                                </IconButton>
                            </Tooltip>
                        ) : null}
                    </div>
                </Toolbar>
                <Table>
                    <TableHead>
                        <TableRow>
                            <Checkbox
                                indeterminate={this.state.selected.length > 0 && this.state.selected.length < this.props.translations.length}
                                checked={this.state.selected.length === this.props.translations.length}
                                onChange={this.handleSelectAllClick}
                                color="primary"
                            />
                            {this.state.titles.map(
                                title => (
                                    <TableCell key={title.displayName}>
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
                                    hover
                                    onClick={event => this.handleClick(event, row.id)}
                                    role="checkbox"
                                    aria-checked={isSelected}
                                    tabIndex={-1}
                                    selected={isSelected}>
                                    <TableCell>
                                        <Checkbox color="primary" checked={isSelected}/>
                                    </TableCell>
                                    <TableCell component="th" scope="row" padding="none">
                                        {row.translation.word.value}
                                    </TableCell>
                                    <TableCell>{row.translation.value}</TableCell>
                                    <TableCell>{row.insertDate}</TableCell>
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
                    onChangePage={this.handleChangePage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </Paper>
        );
    }
}

export default withStyles(styles)(withSnackbar(TranslationList));