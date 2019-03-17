import {withStyles} from "@material-ui/core";
import {fade} from "@material-ui/core/styles/colorManipulator";
import SearchIcon from "@material-ui/icons/Search";
import React from "react";
import InputBase from "@material-ui/core/InputBase";
import * as PropTypes from "prop-types";

const styles = theme => ({
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

});

class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            search: ''
        }
    }

    handleSearchChange = (event) => {
        this.setState({search: event.target.value});
        this.props.onQueryParamsChange({search: event.target.value});
    };

    render() {
        const {classes} = this.props;
        return (
            <div className={classes.search}>
                <div className={classes.searchIcon}>
                    <SearchIcon/>
                </div>
                <InputBase
                    value={this.state.search}
                    onChange={this.handleSearchChange}
                    placeholder="Search…"
                    classes={{
                        root: classes.inputRoot,
                        input: classes.inputInput,
                    }}
                />
            </div>
        )
    }
}

SearchBar.propTypes = {
    onQueryParamsChange: PropTypes.func
};
export default withStyles(styles)(SearchBar);