import React, { useEffect, useState }  from 'react';
import axios from "axios";
import { useTheme } from '@table-library/react-table-library/theme';
import { DEFAULT_OPTIONS, getTheme } from '@table-library/react-table-library/chakra-ui';
import { Box } from '@chakra-ui/react';
import {
    Table,
    Header,
    HeaderRow,
    Body,
    Row,
    HeaderCell,
    Cell,
  } from "@table-library/react-table-library/table";
import '../../styles/util.css'
import PopUpUser from '../PopUpUser';

const LoadUsers = props => {
    const chakraTheme = getTheme(DEFAULT_OPTIONS);
    const theme = useTheme(chakraTheme);
    const [getUsers, setGetUsers] = useState({nodes: [],});
    const [popUpActive, setPopUpActive] = useState(false);
    const [getUser, setGetUser] = useState({});
    const config = {
      headers: { Authorization: `Bearer ${props.token}` }
    };

    useEffect(() => {
        async function fetchUsers(params) {
            const URL = "/users";
            try {
                const response = await axios.get(URL,config);
                setGetUsers({
                  nodes: response.data,
                });
            } catch (error) {
                console.log(error);
            }
        }
        fetchUsers();
    }, []);

    const loadUser = (e) => {
      async function fetchUser(params) {
        const URL = `/users/${params.user.id}`;
        
        try {
            const response = await axios.get(URL, config);
            setGetUser(response);
            setPopUpActive(true);
        } catch (error) {
            console.log(error);
        }
      }
      fetchUser(e);
    }

    // if (!getUsers.nodes.length) return <h3>loading..</h3>;

    return (
      <div>
        <div className='table-header'>
          <Box p={3} borderWidth="1px" borderRadius="lg">
            <Table data={getUsers} theme={theme}>
            {(tableList) => (
              <>
                <Header>
                  <HeaderRow>
                    <HeaderCell>id</HeaderCell>
                    <HeaderCell>name</HeaderCell>
                    <HeaderCell>view</HeaderCell>
                  </HeaderRow>
                </Header>
                <Body>
                  {tableList.map((user, i) => (
                    <Row key={i} item={user}>
                      <Cell>{i}</Cell>
                      <Cell>{user.name}</Cell>
                      <Cell onClick={() => loadUser({user})}><div className="plainborder">details</div></Cell>
                    </Row>
                  ))}
                </Body>
              </>
            )}
          </Table>
          </Box>
        </div>
        <div >
        { popUpActive ? <PopUpUser active={popUpActive} setActive={setPopUpActive} child={getUser} token={props.token}/> : null }
        </div>
      </div>
    );
};

export default LoadUsers;