import React, { useEffect, useState }  from 'react';
import axios from "axios";
import '../NewForm.css'
import '../menu/buttons-small.css';
import PostService from '../PostService';

const NewPropertyByOwner = props => {
    const API_URL = "/properties/owner";
    const [property, setProperty] = useState({
        title: "",
        propertyTypeId: 1,
    });  
    const [msg, setMsg] = useState("");
    const config = {
        headers: { Authorization: `Bearer ${props.token}` }
    };
    const [getTypes, setGetTypes] = useState([]);
    const handleChange = (e) => {
        const value = e.target.value;
        setProperty({ ...property, [e.target.name]: value })
    }

    const RegisterProperty = (e) => {
        e.preventDefault();
        console.log(property);  
        PostService.saveEntity(API_URL, property, config)
            .then((res) => {
                console.log("Property Added Successfully");
                setMsg("Property Added Sucessfully");
                setProperty({
                    title: "",
                    propertyTypeId: 1,
                })
                updateButton();
            }).catch((error) => {
                console.log(error);
                updateButton();
            });
    }

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
            <form class="form" onSubmit={(e) => RegisterProperty(e)}>
                <div className="divborder">
                    <span class="title">add new property</span>
                </div>
                <div>
                    <div className="divborder">
                        <input placeholder="enter property title" name="title" onChange={(e) => handleChange(e)} value={property.title} />
                    </div>
                    <div className="divborder">
                        <select name="propertyTypeId" onChange={(e) => handleChange(e)} value={property.propertyTypeId}>
                            {
                            getTypes.map((type,i) =>
                                <option key={i} value={type.id}>{type.type}</option>
                            )};
                        </select>
                    </div>
                    <button class="modern-small embossed-link-small" type="submit">Submit</button>
                </div>
            </form>
        </div>
    );
};

export default NewPropertyByOwner;