import React from 'react';
import './PopUp.css';
import HomeIcon from '../styles/images/home_icon.png';


const PopUpProperty = ({ active, setActive, child}) => {
    const getImagePath = (e) => {
        if(e === null) {
          return {HomeIcon}.HomeIcon;
        } else {
          return `data:image/jpeg;base64,${e}`;
        }
    };

    return (
        <div className={active ? "popupc active" : "popupc"} onClick={ () => setActive(false)}>
            <div className={active ? "popupc_content active" : "popupc_content"} onClick={e => e.stopPropagation()}>
                <div >
                    <p>id: {child.data.id}</p>
                </div>
                <div >
                    <p>title: {child.data.title}</p>
                </div>
                <div >
                    <p>property type: {child.data.propertyType}</p>
                </div>
                <div >
                    <img className="thumbnail-large"
                      src={getImagePath(child.data.data)}
                      alt=""
                    />
                </div>
            </div>
        </div>
    );
};

export default PopUpProperty;