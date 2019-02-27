import React, {Component} from 'react';
import {Table, withStyles} from '@material-ui/core';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import Tooltip from "@material-ui/core/Tooltip";
import TablePagination from "@material-ui/core/TablePagination";

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
            sortField: 'id',
            sortDirection: 'asc',
            page: 0,
            size: 10
        };

        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
        this.handleChangePage = this.handleChangePage.bind(this);

    }

    componentDidMount() {
        this.props.onQueryParamsChange(this.state);
    }

    handleChangeSort(field) {
        let sortDirection = 'desc';

        if (this.state.sortField === field && this.state.sortDirection === 'desc') {
            sortDirection = 'asc';
        }
        console.log(this.state.sortField);
        this.setState({
            sortDirection: sortDirection,
            sortField: field
        }, () => this.props.onQueryParamsChange(this.state));
    };

    handleChangePage = (event, page) => {
        this.setState({page: page}, () => this.props.onQueryParamsChange(this.state));
    };

    handleChangeRowsPerPage(event) {
        this.setState({size: event.target.value}, () => this.props.onQueryParamsChange(this.state));
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
                                    <TableCell key={title}
                                    >
                                        <Tooltip
                                            title="Sort"
                                            enterDelay={300}
                                        >
                                            <TableSortLabel
                                                active={this.state.sortField === title.path}
                                                direction={this.state.sortDirection}
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
                                <TableRow key={row.id}
                                >
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
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={parseInt(this.props.totalElements)}
                    rowsPerPage={parseInt(this.state.size)}
                    page={parseInt(this.state.page)}
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

export default withStyles(styles)(TranslationList);