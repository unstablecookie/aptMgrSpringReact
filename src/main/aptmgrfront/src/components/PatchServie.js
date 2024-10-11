import axios from "axios";

class PatchService {

    saveEntity(API_URL, entity, config) {
        return axios.patch(API_URL, entity ,config);
    }
}

export default new PatchService;