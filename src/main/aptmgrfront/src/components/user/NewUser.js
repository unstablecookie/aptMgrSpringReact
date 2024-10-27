import React, { useEffect, useState }  from 'react';
import axios from "axios";
import '../NewForm.css'
import '../menu/buttons-small.css';
import PostService from '../PostService';

const NewUser = props => {
    const API_URL = "/users";
    const [user, setUser] = useState({
        name: "",
        password: "",
        roleId: 1,
        isNotLocked: true,
    });  
    const [msg, setMsg] = useState("");
    const config = {
        headers: { Authorization: `Bearer ${props.token}` }
    };
    const [getRoles, setGetRoles] = useState([]);
    const handleChange = (e) => {
        const value = e.target.value;
        setUser({ ...user, [e.target.name]: value })
    }

    const RegisterUser = (e) => {
        e.preventDefault();
        console.log(user);
        PostService.saveEntity(API_URL, user, config)
            .then((res) => {
                console.log("User Added Successfully");
                setMsg("Used Added Sucessfully");
                setUser({
                    name: "",
                    password: "",
                    roleId: 1,
                    isNotLocked: true,

                })
                updateButton();
            }).catch((error) => {
                console.log(error);
                updateButton();
            });
    }

    useEffect(() => {
        async function fetchRoles(params) {
            const URL = "/users/roles";
            try {
                const response = await axios.get(URL,config);
                setGetRoles(
                  response.data,
                );
            } catch (error) {
                console.log(error);
            }
        }
        fetchRoles();
    }, []);

    const updateButton = () => {
        props.hide(false);
    };

    return(
        <div>
            <div>
                {msg && <p>{msg}</p>}
            </div>
            <form className="form" onSubmit={(e) => RegisterUser(e)}>
                <div className="divborder">
                    <span className="title">sign up</span>
                </div>
                <div>
                    <div className="divborder">
                        <input className='simple-input' placeholder="Enter your username" name="name" onChange={(e) => handleChange(e)} value={user.name} />
                    </div>
                    <div className="divborder">
                        <input className='simple-input' placeholder="Enter your password" name="password" onChange={(e) => handleChange(e)} value={user.password} />
                    </div>
                    <div className="divborder">
                        <select className='simple-input' name="roleId" onChange={(e) => handleChange(e)} value={user.roleId}>
                            {
                            getRoles.map((role,i) =>
                                <option key={i} value={role.id}>{role.name}</option>
                            )};
                        </select>
                    </div>
                    <div className="divborder">
                        <select className='simple-input' name="isNotLocked" onChange={(e) => handleChange(e)} value={user.isNotLocked}>
                            <option value={true}>ENABLED</option>
                            <option value={false}>DISABLE</option>
                        </select>
                    </div>
                    <div className="divborder">
                        <button className="modern-small embossed-link-small" type="submit">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default NewUser;