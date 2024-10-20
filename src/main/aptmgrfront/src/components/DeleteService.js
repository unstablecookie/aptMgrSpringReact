import axios from "axios";

class DeleteService {

    deleteEntity(API_URL, config) {
        return axios.delete(API_URL, config);
    }
}

export default new DeleteService;