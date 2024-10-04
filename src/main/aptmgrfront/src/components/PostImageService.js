import axios from "axios";

class PostImageService {
    
    saveEntity(API_URL, entity, config) {
        const formData = new FormData();
        formData.append('imageFile', entity);
        return axios.post(API_URL, formData,config)
        .catch(error => {
            console.error(error);
          });
    }
}

export default new PostImageService;