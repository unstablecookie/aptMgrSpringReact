import axios from "axios";

class PostService {

    saveEntity(API_URL, entity, config) {
        return axios.post(API_URL, entity, config);
    }
}

export default new PostService;