import React, { useEffect, useState }  from 'react';
import PostImageService from '../PostImageService';

const UploadImage = props => {
    const API_URL = "/properties/image";
    const [selectedImage, setSelectedImage] = useState(null);
    const config = {
        headers: { Authorization: `Bearer ${props.token}`, 'Content-Type': 'multipart/form-data' }
    };

    const UploadSelectedImage = (e) => {
        e.preventDefault();
        PostImageService.saveEntity(API_URL, selectedImage, config)
            .then(response => {
                console.log(response.data);
            })
            .catch((error) => {
                console.log(error);
        });
    };

    return (
        <div>
            <form class="form" onSubmit={(e) => UploadSelectedImage(e)}>
                <input type="file" name="myImage" onChange={(event) => {setSelectedImage(event.target.files[0]);}}/>
                <button class="modern-small embossed-link-small" type="submit">Submit</button>
            </form>
        </div>
    );
};

export default UploadImage;