import React, {Component} from 'react';
import {Table, withStyles} from '@material-ui/core';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";

const styles = {};

class TranslationList extends Component {

    render() {
        const {translations} = this.props;

        return (
            <React.Fragment>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>
                                Word
                            </TableCell>
                            <TableCell>
                                Translation
                            </TableCell>
                            <TableCell>
                                Insert Date
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>{
                        translations.map(row => {
                            return (
                                <TableRow
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
            </React.Fragment>
        );
    }
}

export default withStyles(styles)(TranslationList);