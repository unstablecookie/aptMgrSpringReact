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

const LoadUsers = props => {
    const chakraTheme = getTheme(DEFAULT_OPTIONS);
    const theme = useTheme(chakraTheme);
    const [getUsers, setGetUsers] = useState({nodes: [],});
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

    if (!getUsers.nodes.length) return <h3>loading..</h3>;

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
                  </HeaderRow>
                </Header>
                <Body>
                  {tableList.map((user, i) => (
                    <Row key={i} item={user}>
                      <Cell>{i}</Cell>
                      <Cell>{user.name}</Cell>

                    </Row>
                  ))}
                </Body>
              </>
            )}
          </Table>
          </Box>
        </div>
      </div>
    );
};

export default LoadUsers;