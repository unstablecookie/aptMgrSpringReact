import React, { useState }  from 'react';
import './PopUp.css';
import '../components/Border.css';
import HomeIcon from '../styles/images/home_icon.png';
import PatchServie from './PatchServie';
import RightRibbon from './menu/RightRibbon';


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
    // const [property, setProperty] = useState({
    //     lastPayment: child.data.lastPayment,
    //     monthlyPaid: paidCheck,
    // });

    const [updateFullProperty, setUpdateFullProperty] = useState({
        title: child.data.title,
        buildIn: child.data.buildIn,
        floorsNumb: child.data.floorsNumb,
        sqrMeters: child.data.sqrMeters,
        lastPayment: child.data.lastPayment,
        monthlyPaid: paidCheck,
    });

    const handlePaidSwich = (e) => {
        // e.preventDefault();
        const URL = `/properties/owner/${child.data.id}/paid`;
        const updateProperty = {
            lastPayment: updateFullProperty.lastPayment,
            monthlyPaid: !paidCheck,
        }
        PatchServie.updateEntity(URL, updateProperty, config)
            .then(() => {
                // setProperty({
                //     lastPayment: updateProperty.lastPayment,
                //     monthlyPaid: updateProperty.monthlyPaid,
                // })
                setPaidCheck(!paidCheck);
            })
            .catch((error) => {
            console.log(error);
            // setProperty({
            //     lastPayment: child.data.monthlyPaid,
            //     monthlyPaid: paidCheck,
            // })
        });
    }
    const handleChange = (e) => {
        const value = e.target.value;
        setUpdateFullProperty({ ...updateFullProperty, [e.target.name]: value })
    }

    const changeDate = (e) => {
        var value = e.target.value.replace('T',' ');
        if (value.length < 18) {
            value = value + ":00";
        }
        setUpdateFullProperty({ ...updateFullProperty, [e.target.name]: value })
    }

    return (
        <div className={active ? "popupc active" : "popupc"} onClick={ () => setActive(false)}>
            <div className={active ? "popupc_content active" : "popupc_content"} onClick={e => e.stopPropagation()}>
                <div className='sep-blocks'>
                    <div>
                        <label className='popup-line-text'>Id: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='number' value={child.data.id} readOnly/>
                    </div>
                    <div>
                        <label className='popup-line-text'>Title: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='text' name='title' defaultValue={child.data.title} onChange={(e) => handleChange(e)}/>
                    </div>
                    <div>
                        <label className='popup-line-text'>Property type:</label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='text' value={child.data.propertyType} readOnly/>
                    </div>
                    <div>
                        <label className='popup-line-text'>Build in: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='number' name='buildIn' defaultValue={child.data.buildIn} onChange={(e) => handleChange(e)}/>
                    </div>
                    <div>
                        <label className='popup-line-text'>Floors: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='number' name='floorsNumb' defaultValue={child.data.floorsNumb} onChange={(e) => handleChange(e)}/>
                    </div>
                    <div>
                        <label className='popup-line-text'>Square meters: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='number' name='sqrMeters' defaultValue={child.data.sqrMeters} onChange={(e) => handleChange(e)}/>
                    </div>
                    <div>
                        <label className='popup-line-text'>Last payment: </label>
                    </div>
                    <div className="divborder">
                        <input className='popup-line' type='datetime-local' name='lastPayment' step='1' defaultValue={child.data.lastPayment} onChange={(e) => changeDate(e)}/>
                    </div>
                    <div>
                    <label className='popup-line-text'>Is it paid? </label>
                        <div className='switch bottom-line-gap'>
                            <label className="switch">
                                <input type="checkbox" checked={paidCheck} onChange={(e) => handlePaidSwich(e)}/>
                                <span className="slider square"></span>
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
                <div className='side-panel'>
                    <RightRibbon child={child} token={token} popUpActive={setActive} entity="properties" entityFull={updateFullProperty}/>
                </div>
            </div>
        </div>
    );
};

export default PopUpProperty;