import React, { useEffect, useState }  from 'react';
import axios from "axios";
import '../NewForm.css'
import '../menu/buttons-small.css';
import PostService from '../PostService';

const NewProperty = props => {
    const API_URL = "/properties";
    const [property, setProperty] = useState({
        title: "",
        propertyTypeId: 1,
        buildIn: 1,
        floorsNumb: 1,
        sqrMeters: 1,
        lastPayment: new Date().toISOString().replace('T',' ').substring(0, 19),
        monthlyPaid: false,
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

    const changeDate = (e) => {
        const value = e.target.value.replace('T',' ');
        setProperty({ ...property, [e.target.name]: value })
    }

    const RegisterProperty = (e) => {
        e.preventDefault(); 
        PostService.saveEntity(API_URL, property, config)
            .then((res) => {
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
            }).catch((error) => {
                console.log(error);
                updateButton();
            });
    }

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

    const updateButton = () => {
        props.hide(false);
    };

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
                    <div className="divborder">
                        <input placeholder="build in" name="buildIn" onChange={(e) => handleChange(e)} value={property.buildIn} />
                    </div>
                    <div className="divborder">
                        <input placeholder="floors number" name="floorsNumb" onChange={(e) => handleChange(e)} value={property.floorsNumb} />
                    </div>
                    <div className="divborder">
                        <input placeholder="sqr meters" name="sqrMeters" onChange={(e) => handleChange(e)} value={property.sqrMeters} />
                    </div>
                    <div className="divborder">
                        <input type="datetime-local" name="lastPayment" step="1" onChange={(e) => changeDate(e)} value={property.datetime} />
                    </div>
                    <div className="divborder">
                        <button class="modern-small embossed-link-small" type="submit">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default NewProperty;