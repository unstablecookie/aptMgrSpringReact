import React from 'react';
import './buttons.css';
import './button-red.css'

const DeleteButton = props => {
    return (
        <div className='example-wrapper-right-button'>
            <button className="modern-red embossed-link-red">delete</button>
        </div>
    );
};

export default DeleteButton;