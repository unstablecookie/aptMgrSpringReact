import React, { useState }  from 'react';
import './PopUp.css';
import '../components/Border.css';
import UserIcon from './user/UserIcon';
import PatchServie from './PatchServie';
import RightRibbon from './menu/RightRibbon';


const PopUpUser = ({ active, setActive, child, token}) => {
    const config = {
        headers: { Authorization: `Bearer ${token}` }
    };

    const [isNotLocked , setIsNotLocked] = useState(child.data.isNotLocked);
    const [userLock, setUserLock] = useState({
        lastPayment: isNotLocked
    });

    const [updateFullUser, setUpdateFullUser] = useState({
        name: child.data.name,
        isNotLocked: isNotLocked,
    });

    const handleChange = (e) => {
        const value = e.target.value;
        setUpdateFullUser({ ...updateFullUser, [e.target.name]: value })
    }

    const handleLockUser = (e) => {
        const URL = `/users/${child.data.id}/lock/${!isNotLocked}`;
        PatchServie.updateEntity(URL, "",config)
            .then(() => {
                setIsNotLocked(!isNotLocked);
            })
            .catch((error) => {
                console.log(error);
                setIsNotLocked(isNotLocked);
            });        
    }

    return (
        <div className={active ? "popupc active" : "popupc"} onClick={ () => setActive(false)}>
            <div className={active ? "popupc_content active" : "popupc_content"} onClick={e => e.stopPropagation()}>
                <div className='sep-blocks'>
                    <UserIcon />
                    <div>
                        <label className='popup-line-text'>Id: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='number' value={child.data.id} readOnly/>
                    </div>
                    <div>
                        <label className='popup-line-text'>name: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='text' name='name' defaultValue={child.data.name} onChange={(e) => handleChange(e)}/>
                    </div>
                    <div>
                    <label className='popup-line-text'>active user? </label>
                        <div>
                            <label className="switch">
                                <input type="checkbox" checked={isNotLocked} onChange={(e) => handleLockUser(e)}/>
                                <span className="slider square"></span>
                            </label>
                        </div>
                    </div>
                </div>
                <div className='side-panel'>
                    <RightRibbon child={child} token={token} popUpActive={setActive} entity="users" entityFull={updateFullUser}/>
                </div>
            </div>
        </div>
    );
};

export default PopUpUser;