import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/darkribbon.css'
import LogOutButton from './LogOutButton'

const TopRibbon = props => {
    const history = useNavigate();
    const onClickLogOut = () => history("/");
    return (
        <div className="example-wrapper-top">
            <h3 >{props.topHeader}</h3>
            <div onClick={onClickLogOut}>
                <LogOutButton />
            </div>

        </div>
    );
};

export default TopRibbon;