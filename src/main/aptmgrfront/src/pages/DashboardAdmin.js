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
  const [showUsers, setShowUsers] = React.useState(true);
  const [showProperties, setShowProperties] = React.useState(false);
  const onClickShowUsers = () => {
    setShowUsers(true);
    setShowProperties(false);
  }

  const onClickShowProperties = () => {
    setShowUsers(false);
    setShowProperties(true);
  }

  const [showNewUser, setShowNewUser] = React.useState(false);
  const onClickNewUser = () => setShowNewUser(!showNewUser);

  const [showNewProperty, setShowNewProperty] = React.useState(false);
  const onClickNewProperty = () => setShowNewProperty(!showNewProperty);

  const [showListUsers, setShowListUsers] = React.useState(false);
  const onClickListUsers = () => setShowListUsers(!showListUsers);

  const [showListProperties, setShowListProperties] = React.useState(false);
  const onClickListProperties = () => setShowListProperties(!showListProperties);
  const toph3 = 'Admin Dashboard';

  const DashboardUsers = (e) => {
    return (
      <div>
          <div onClick={onClickNewUser}>
             <NewUserButton />
           </div>
           <div>
             { showNewUser ? <NewUser token={props.apptoken} hide={setShowNewUser}/> : null }
           </div>
           <div onClick={onClickListUsers}>
             <ListUsersButton />
           </div>
           <div>
             { showListUsers ? <LoadUsers token={props.apptoken}/> : null }
           </div>
      </div>
    );
  };

  const DashboardProperties = (e) => {
    return (
      <div>
        <div onClick={onClickNewProperty}>
             <NewPropertyButton />
        </div>
        <div>
             { showNewProperty ? <NewPropertyByOwner token={props.apptoken} hide={setShowNewProperty}/> : null }
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

    return (
        <div>
          <header className="App-header" />
          <TopRibbon topHeader={toph3}/>
          <button className='button-dashboard' onClick={onClickShowUsers}>Users</button>
          <button className='button-dashboard' onClick={onClickShowProperties}>Properties</button>
          <div>
            { showUsers ? <DashboardUsers /> : null } 
          </div>
          <div>
            { showProperties ? <DashboardProperties /> : null } 
          </div>
      </div>
    );
};

export default DashboardAdmin;