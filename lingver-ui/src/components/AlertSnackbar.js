import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';

class AlertSnackbar extends React.Component {
    state = {
        open: true,
        vertical: 'top',
        horizontal: 'center',
    };

    handleClose = () => {
        this.setState({open: false});
    };

    render() {
        const {vertical, horizontal, open} = this.state;
        return (
            <div>
                <Snackbar
                    style={{marginTop: '4em'}}
                    anchorOrigin={{vertical, horizontal}}
                    open={open}
                    onClose={this.handleClose}
                    ContentProps={{
                        'aria-describedby': 'message-id',
                    }}
                    message={<span id="message-id">{this.props.message}</span>}
                />
            </div>
        );
    }
}

export default AlertSnackbar;
