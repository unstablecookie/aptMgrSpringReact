import React, { useEffect, useState } from 'react';
import '../styles/common.css';
import '../styles/darkribbon.css';
import NewUser from '../components/user/NewUser';
import NewPropertyByOwner from '../components/property/NewPropertyByOwner';
import NewUserButton from '../components/menu/NewUserButton';
import NewPropertyButton from '../components/menu/NewPropertyButton';
import ListUsersButton from '../components/menu/ListUsersButton';
import LoadUsers from '../components/user/LoadUsers';
import LoadProperties from '../components/property/LoadProperties';
import ListPropertiesButton from '../components/menu/ListPropertiesButton';
import TopRibbon from '../components/menu/TopRibbon';

const DashboardAdmin = props => {

    const [showNewUser, setShowNewUser] = React.useState(false);
    const onClickNewUser = () => setShowNewUser(!showNewUser);
  
    const [showNewProperty, setShowNewProperty] = React.useState(false);
    const onClickNewProperty = () => setShowNewProperty(!showNewProperty);
  
    const [showListUsers, setShowListUsers] = React.useState(false);
    const onClickListUsers = () => setShowListUsers(!showListUsers);
  
    const [showListProperties, setShowListProperties] = React.useState(false);
    const onClickListProperties = () => setShowListProperties(!showListProperties);
    const toph3 = 'Admin Dashboard';

    return (
        <div>
          <header className="App-header" />
          <TopRibbon topHeader={toph3}/>
          <div onClick={onClickNewUser}>
            <NewUserButton />
          </div>
          <div>
            { showNewUser ? <NewUser token={props.apptoken} hide={setShowNewUser}/> : null }
          </div>
          <div onClick={onClickNewProperty}>
            <NewPropertyButton />
          </div>
          <div>
            { showNewProperty ? <NewPropertyByOwner token={props.apptoken} hide={setShowNewProperty}/> : null }
          </div>
          <div onClick={onClickListUsers}>
            <ListUsersButton />
          </div>
          <div>
            { showListUsers ? <LoadUsers token={props.apptoken}/> : null }
          </div>
          <div onClick={onClickListProperties}>
            <ListPropertiesButton />
          </div>
          <div>
            { showListProperties ? <LoadProperties token={props.apptoken}/> : null }
          </div>
      </div>
    );
};

export default DashboardAdmin;