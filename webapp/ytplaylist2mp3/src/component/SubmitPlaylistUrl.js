import React, { Component } from 'react';

class SubmitPlaylistUrl extends Component {
    render() {
        return (
            <div>
                <input type="text" id="inputPlaylistUrl" placeholder="Playlist Url" />
                <input type="submit" />
            </div>
        )
    }
}

export default SubmitPlaylistUrl