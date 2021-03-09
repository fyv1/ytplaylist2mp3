import React, { Component } from 'react';
import YtDownloaderService from '../service/YtDownloaderService';

class ListPlaylistItemsComponent extends Component {
    
    constructor(props) {
        super(props)
        this.state = {
            items: [],
            message: null
        }
        this.getPlItems = this.getPlItems.bind(this)
    }

    componentDidMount() {
        this.getPlItems();
    }

    getPlItems() {
        YtDownloaderService.getPlaylistItems("PLnEiN3g9RA7TXThKtggHY65_QOkR98kf2")
            .then(
                response => {
                    console.log(response);
                    this.setState({items: response.data})
                }
            )
    }

    render() {
        return (
            <div className="container">
            <h3>Youtube Playlist Items to Download as MP3</h3>
            <div className="container">
                <table className="table">
                    <thead>
                        <tr>
                            <th>Thumbnail</th>
                            <th>Title</th>
                            <th>Download</th>
                            <th>Listen on YT</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.items.map(
                                item =>
                                <tr key={item.title}>
                                    <td><img src={`${item.thumbnailUrl}`} width="120px" height="90px" /></td>
                                    <td>{item.title}</td>
                                    <td><a href={"http://localhost:8082/api/video/"+item.videoId}>Download</a></td>
                                    <td><a href={"https://youtu.be/watch?v="+item.videoId}>Listen on YT</a></td>
                                </tr>
                            )
                        }

                        <tr>

                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        )
    }
}

export default ListPlaylistItemsComponent