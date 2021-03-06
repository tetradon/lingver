import React, {Component} from 'react';
import {withStyles} from '@material-ui/core';
import Tooltip from "@material-ui/core/Tooltip";
import {withSnackbar} from "notistack";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from '@material-ui/icons/Delete';
import {fade, lighten} from '@material-ui/core/styles/colorManipulator';
import classNames from 'classnames';
import ToolbarSearch from "./ToolbarSearch";
import * as PropTypes from "prop-types";
import ExerciseMenu from "./ExerciseMenu";
import {Trans} from "react-i18next";

const styles = theme => ({
    root: {
        paddingRight: theme.spacing.unit,
    },
    highlight:
        theme.palette.type === 'light'
            ? {
                color: theme.palette.primary.main + '!important',
                backgroundColor: lighten(theme.palette.primary.light, 0.85) + '!important',
            }
            : {
                color: theme.palette.text.primary + '!important',
                backgroundColor: theme.palette.primary.dark + '!important',
            },
    spacer: {
        flex: '1 1 100%',
    },
    title: {
        flex: '0 0 auto',
    },
    search: {
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: fade(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: fade(theme.palette.common.white, 0.25),
        },
        marginLeft: 0,
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            marginLeft: theme.spacing.unit,
            width: 'auto',
        },
    },
    searchIcon: {
        width: theme.spacing.unit * 9,
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    inputRoot: {
        color: 'inherit',
        width: '100%',
    },
    inputInput: {
        paddingTop: theme.spacing.unit,
        paddingRight: theme.spacing.unit,
        paddingBottom: theme.spacing.unit,
        paddingLeft: theme.spacing.unit * 10,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            width: 120,
            '&:focus': {
                width: 200,
            },
        },
    },
    stickyToolbar: {
        position: 'sticky',
        top: 0,
        zIndex: 10,
        background: '#fff'
    }
});

class TableToolbar extends Component {

    render() {

        const {classes, selected} = this.props;
        return (
            <Toolbar
                className={classNames(classes.root, {
                    [classes.highlight]: selected.length > 0,
                }, classes.stickyToolbar)}
            >
                <div className={classes.title}>
                    {
                        selected.length > 0 ?
                            <Typography color="inherit" variant="subtitle1">
                                {selected.length} <Trans>selected</Trans>
                            </Typography>
                            :
                            <Typography variant="h5" id="tableTitle">
                                <Trans>Your dictionary</Trans>
                            </Typography>
                    }
                </div>
                <div className={classes.spacer}/>
                <ExerciseMenu selected={this.props.selected} hidden={selected.length === 0}
                              onQueryParamsChange={this.props.onQueryParamsChange}
                              clearSelected={this.props.clearSelected}
                />
                <ToolbarSearch hidden={selected.length > 0}
                               onQueryParamsChange={this.props.onQueryParamsChange}/>
                <div className={classes.actions}>
                    {
                        selected.length > 0 ?
                            (
                                <Tooltip title={<Trans>Delete</Trans>}>
                                    <IconButton onClick={this.props.onDelete}>
                                        <DeleteIcon/>
                                    </IconButton>
                                </Tooltip>
                            )
                            : null
                    }
                </div>
            </Toolbar>
        )
    };
}

TableToolbar.propTypes = {
    onQueryParamsChange: PropTypes.func,
    clearSelected: PropTypes.func,
    onDelete: PropTypes.func,
    selected: PropTypes.array,
};
export default withStyles(styles)(withSnackbar(TableToolbar));