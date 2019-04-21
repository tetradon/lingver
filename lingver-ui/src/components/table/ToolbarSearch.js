import {withStyles} from "@material-ui/core";
import {fade} from "@material-ui/core/styles/colorManipulator";
import SearchIcon from "@material-ui/icons/Search";
import React from "react";
import InputBase from "@material-ui/core/InputBase";
import * as PropTypes from "prop-types";
import {withTranslation} from "react-i18next";

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

class ToolbarSearch extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            search: '',
            typing: false,
            typingTimeout: 0,
        }
    }

    handleSearchChange = (event) => {

        if (this.state.typingTimeout) {
            clearTimeout(this.state.typingTimeout);
        }

        this.setState({
            search: event.target.value,
            typing: false,
            typingTimeout: setTimeout(() => {
                this.props.onQueryParamsChange({search: this.state.search});
            }, 100)
        });
    };

    render() {
        const {t} = this.props;
        const {classes} = this.props;
        if (!this.props.hidden) {
            return (
                <div className={classes.search}>
                    <div className={classes.searchIcon}>
                        <SearchIcon/>
                    </div>
                    <InputBase
                        value={this.state.search}
                        onChange={this.handleSearchChange}
                        placeholder={t('Search')}
                        classes={{
                            root: classes.inputRoot,
                            input: classes.inputInput,
                        }}
                    />

                </div>
            )
        }
        return null;
    }
}

ToolbarSearch.propTypes = {
    onQueryParamsChange: PropTypes.func,
    hidden: PropTypes.bool
};
export default withTranslation()(withStyles(styles)(ToolbarSearch));