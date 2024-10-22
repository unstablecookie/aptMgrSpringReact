import React, { useEffect, useState }  from 'react';
import axios from "axios";
import '../NewForm.css'
import '../menu/buttons-small.css';
import PostService from '../PostService';
import PostImageService from '../PostImageService';

const NewPropertyByOwner = props => {
    const API_URL = "/properties/owner";
    const API_IMG_URL = "/properties/image";
    const [property, setProperty] = useState({
        title: "",
        propertyTypeId: 1,
        buildIn: 1,
        floorsNumb: 1,
        sqrMeters: 1,
        lastPayment: new Date().toISOString().replace('T',' ').substring(0, 19),
        monthlyPaid: false,
    });
    const currDate = new Date().toISOString().replace('T',' ').substring(0, 19);
    const [msg, setMsg] = useState("");
    const config = {
        headers: { Authorization: `Bearer ${props.token}` }
    };
    const [getTypes, setGetTypes] = useState([]);
    const [selectedImage, setSelectedImage] = useState(null);
    const handleChange = (e) => {
        const value = e.target.value;
        setProperty({ ...property, [e.target.name]: value })
    }

    const changeDate = (e) => {
        const value = e.target.value.replace('T',' ');
        setProperty({ ...property, [e.target.name]: value })
    }

    const RegisterProperty = (e) => {
        e.preventDefault();
        PostService.saveEntity(API_URL, property, config)
            .then((res) => {
                console.log("Property Added Successfully");
                setMsg("Property Added Sucessfully");
                setProperty({
                    title: "",
                    propertyTypeId: 1,
                    buildIn: 1,
                    floorsNumb: 1,
                    sqrMeters: 1,
                    lastPayment: new Date().toISOString().replace('T',' ').substring(0, 19),
                    monthlyPaid: false,
                })
                updateButton();
                UploadSelectedImage(res.data.id);
            })
            .catch((error) => {
                console.log(error);
                updateButton();
            });
    }

    const UploadSelectedImage = (e) => {
        if (selectedImage === null) {
            return;
        }
        const config_img = {
            headers: { Authorization:
                `Bearer ${props.token}`,
                'Content-Type': 'multipart/form-data',
                'X-property-id': e}
        };
        PostImageService.saveEntity(API_IMG_URL, selectedImage, config_img)
            .then(response => {
                console.log(response.data);
            })
            .catch((error) => {
                console.log(error);
        });
    };

    const updateButton = () => {
        props.hide(false);
    };

    useEffect(() => {
        async function fetchTypes(params) {
            const URL = "/properties/types";
            try {
                const response = await axios.get(URL,config);
                setGetTypes(
                  response.data,
                );
            } catch (error) {
                console.log(error);
            }
        }
        fetchTypes();
    }, []);

    return(
        <div>
            <div className="divborder">
                {msg && <p>{msg}</p>}
            </div>
            <form className="form" onSubmit={(e) => RegisterProperty(e)}>
                <div className="divborder">
                    <span className="title">add new property</span>
                </div>
                <div>
                    <div className="divborder">
                        <input className='simple-input' placeholder="enter property title" name="title" onChange={(e) => handleChange(e)} value={property.title} />
                    </div>
                    <div className="divborder">
                        <select className='simple-input' name="propertyTypeId" onChange={(e) => handleChange(e)} value={property.propertyTypeId}>
                            {
                            getTypes.map((type,i) =>
                                <option key={i} value={type.id}>{type.type}</option>
                            )};
                        </select>
                    </div>
                    <div className="divborder">
                        <input className='simple-input' placeholder="build in" name="buildIn" onChange={(e) => handleChange(e)} />
                    </div>
                    <div className="divborder">
                        <input className='simple-input' placeholder="floors number" name="floorsNumb" onChange={(e) => handleChange(e)} />
                    </div>
                    <div className="divborder">
                        <input className='simple-input' placeholder="sqr meters" name="sqrMeters" onChange={(e) => handleChange(e)} />
                    </div>
                    <div className="divborder">
                        <input type="datetime-local" name="lastPayment" step="1" defaultValue={currDate} onChange={(e) => changeDate(e)}/>
                    </div>
                    <div className="divborder">
                        <input className='simple-input' type="file" name="myImage" onChange={(e) => {setSelectedImage(e.target.files[0]);}}/>
                    </div>
                    <button className="modern-small embossed-link-small" type="submit">Submit</button>
                </div>
            </form>
        </div>
    );
};

export default NewPropertyByOwner;