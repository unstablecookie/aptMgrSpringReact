import React, { useState } from 'react'; 
import '../styles/errorPage.css';
import TopRibbon from '../components/menu/TopRibbon';
import ErrorImage from '../styles/images/error.jpg'

const Error = props => {
    const toph3 = 'ERROR';
    return (
        <div>
            <div>
                <TopRibbon  topHeader={toph3}/>
            </div>
        <div className='error-container'>
            <div className='form-error'>
                <h2>ERROR. SOMETHING WENT WRONG .</h2>               
            </div>   
        </div>
            <div className='error-container-image'>
                <img src={ErrorImage} />
            </div>
        </div>
    );
};


export default Error;