import React, { useState }  from 'react';
import axios from "axios";
import './PopUp.css';
import HomeIcon from '../styles/images/home_icon.png';
import PatchServie from './PatchServie';


const PopUpProperty = ({ active, setActive, child, token}) => {
    const config = {
        headers: { Authorization: `Bearer ${token}` }
    };

    const getImagePath = (e) => {
        if(e === null) {
          return {HomeIcon}.HomeIcon;
        } else {
          return `data:image/jpeg;base64,${e}`;
        }
    };

    const [paidCheck , setPaidCheck] = useState(child.data.monthlyPaid);
    const [property, setProperty] = useState({
        lastPayment: child.data.lastPayment,
        monthlyPaid: paidCheck,
    });

    
    const handlePaidSwich = (e) => {
        e.preventDefault();
        const URL = `/properties/owner/${child.data.id}/paid`;
        const updateProperty = {
            lastPayment: new Date().toISOString().replace('T',' ').substring(0, 19),
            monthlyPaid: true,
        }
        PatchServie.saveEntity(URL, updateProperty, config)
            .then(() => {
                setProperty({
                    lastPayment: updateProperty.lastPayment,
                    monthlyPaid: updateProperty.monthlyPaid,
                })
                setPaidCheck(true);
            })
            .catch((error) => {
            console.log(error);
            setProperty({
                lastPayment: child.data.monthlyPaid,
                monthlyPaid: paidCheck,
            })
        });
        
      }

    return (
        <div className={active ? "popupc active" : "popupc"} onClick={ () => setActive(false)}>
            <div className={active ? "popupc_content active" : "popupc_content"} onClick={e => e.stopPropagation()}>
                <div className='sep-blocks'>
                    <div>
                        <text className='popup-line'>Id: </text><p className='popup-line'>{child.data.id}</p>
                    </div>
                    <div>
                        <text className='popup-line'>Title: </text><p className='popup-line'>{child.data.title}</p>
                    </div>
                    <div>
                        <text className='popup-line'>Property type:</text><p className='popup-line'>{child.data.propertyType}</p>
                    </div>
                    <div>
                        <text className='popup-line'>Build in: </text><p className='popup-line'>{child.data.buildIn}</p>
                    </div>
                    <div>
                        <text className='popup-line'>Floors: </text><p className='popup-line'>{child.data.floorsNumb}</p>
                    </div>
                    <div>
                        <text className='popup-line'>Square meters: </text><p className='popup-line'>{child.data.sqrMeters}</p>
                    </div>
                    <div>
                        <text className='popup-line'>Last payment: </text><p className='popup-line'>{property.lastPayment}</p>
                    </div>
                    <div>
                    <text className='popup-line'>Is it paid? </text>
                        <div className='switch bottom-line-gap'>
                            <label class="switch">
                                <input type="checkbox" checked={paidCheck} defaultChecked={false} onChange={(e) => handlePaidSwich(e)}/>
                                <span class="slider"></span>
                            </label>
                        </div>
                    </div>
                </div>
                <div className='sep-blocks'>
                    <div>
                        <img className="thumbnail-large img-position"
                        src={getImagePath(child.data.data)}
                        alt=""/>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PopUpProperty;