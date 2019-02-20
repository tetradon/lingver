import React from 'react';
import Snackbar from '@material-ui/core/Snackbar';

class AlertSnackbar extends React.Component {
    state = {
        open: true,
        vertical: 'top',
        horizontal: 'center',
        message: 'Wrong credentials'
    };

    handleClose = () => {
        this.setState({open: false});
    };

    render() {
        const {vertical, horizontal, open} = this.state;
        return (
            <div>
                <Snackbar
                    anchorOrigin={{vertical, horizontal}}
                    open={open}
                    onClose={this.handleClose}
                    ContentProps={{
                        'aria-describedby': 'message-id',
                    }}
                    message={<span id="message-id">Wrong Credentials!</span>}
                />
            </div>
        );
    }
}

export default AlertSnackbar;
