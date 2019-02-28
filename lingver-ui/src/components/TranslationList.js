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

const styles = {};

class TranslationList extends Component {

    constructor(props) {
        super(props);
        this.state = {
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

    render() {
        const {translations} = this.props;
        return (
            <React.Fragment>
                <Table>
                    <TableHead>
                        <TableRow>
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
                            return (
                                <TableRow key={row.id}>
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
            </React.Fragment>
        );
    }
}

export default withStyles(styles)(withSnackbar(TranslationList));