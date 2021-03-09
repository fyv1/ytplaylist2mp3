import axios from 'axios'

const BACKEND_API_URL = 'http://localhost:8082'

class YtDownloaderService {

    getPlaylistItems(id) {
        return axios.get(BACKEND_API_URL+'/api/playlist/'+id)
    }

}

export default new YtDownloaderService()