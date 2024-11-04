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
import { usePagination } from "@table-library/react-table-library/pagination";
import '../../styles/util.css'
import PopUpUser from '../PopUpUser';

const LoadUsers = props => {
    const LIMIT = 5;
    const chakraTheme = getTheme(DEFAULT_OPTIONS);
    const theme = useTheme(chakraTheme);
    const [getUsers, setGetUsers] = useState({nodes: [],});
    const [popUpActive, setPopUpActive] = useState(false);
    const [getUser, setGetUser] = useState({});
    const config = {
      headers: { Authorization: `Bearer ${props.token}` },
      params: {
        from: 0,
        size: LIMIT,
      },
    };
    const [counter, setCounter] = useState(0);

    const pagination = usePagination(
      getUsers,
      {
        state: {
          page: 0,
          size: LIMIT,
        },
        onChange: onPaginationChange,
      },
      {
        isServer: true,
      }
    );
    const [search, setSearch] = useState("");

    const handleSearch = (e) => {
      setSearch(e.target.value);
      async function fetchPagedUsersSearch(params) {
        const URL = "/users/search";
        const updateConfig = {
          headers: { Authorization: `Bearer ${props.token}` },
          params: {
            name: params,
            from: 0,
            size: LIMIT,
          },
        };
        try {
            const response = await axios.get(URL, updateConfig);
            setGetUsers({
              nodes: response.data,
            });
        } catch (error) {
            console.log(error);
        }
      }
      fetchPagedUsersSearch(e.target.value);
    };

    function onPaginationChange(action, state) {
      async function fetchPagedUsers(params) {
        const URL = "/users";
        const updateConfig = {
          headers: { Authorization: `Bearer ${props.token}` },
          params: {
            from: (params * LIMIT),
            size: LIMIT,
          },
        };
        try {
            const response = await axios.get(URL, updateConfig);
            setGetUsers({
              nodes: response.data,
            });
        } catch (error) {
            console.log(error);
        }
      }
      async function fetchPagedUsersSearch(params) {
        const URL = "/users/search";
        const updateConfig = {
          headers: { Authorization: `Bearer ${props.token}` },
          params: {
            name: search,
            from: (params * LIMIT),
            size: LIMIT,
          },
        };
        try {
            const response = await axios.get(URL, updateConfig);
            setGetUsers({
              nodes: response.data,
            });
        } catch (error) {
            console.log(error);
        }
      }
      if (search == "") {
        fetchPagedUsers(action.payload.page);
      } else {
        fetchPagedUsersSearch(action.payload.page);
      }
    }

    useEffect(() => {
      async function fetchCount(params) {
        const URL = "/users/count";
        try {
          const response = await axios.get(URL, config);
          var maxEntities;
          if (response.data <= LIMIT) {
            maxEntities = 1;
          } else {
            maxEntities = Number((response.data / LIMIT).toFixed())  + 1;
          }
          setCounter(maxEntities);
          } catch (error) {
          console.log(error);
        }
      }
      fetchCount();
    }, []);

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

    return (
      <div>
        <div className='table-header'>
          {/* htmlFor="search" */}
            <label >
                    Search by Name:
                    <input className='simple-input' id="search" type="text" value={search} onChange={handleSearch}/>
            </label>
          <Box p={3} borderWidth="1px" borderRadius="lg">
            <Table data={getUsers} pagination={pagination} 
              theme={theme}>
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
                      <Cell>{user.id}</Cell>
                      <Cell>{user.name}</Cell>
                      <Cell onClick={() => loadUser({user})}><div className="plainborder">details</div></Cell>
                    </Row>
                  ))}
                </Body>
              </>
            )}
          </Table>
            {true && (
              <div
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                }}
              >
                <span>Total Pages: {counter}</span>

                <span>
                  Page:{" "}
                  {Array(counter)
                    .fill()
                    .map((_, index) => (
                      <button className='modern-nextpage'
                        key={index}
                        type="button"
                        onClick={() => pagination.fns.onSetPage(index)}
                      >
                        {index + 1}
                      </button>
                    ))}
                </span>
              </div>

            )}
          </Box>
        </div>
        <div >
        { popUpActive ? <PopUpUser active={popUpActive} setActive={setPopUpActive} child={getUser} token={props.token}/> : null }
        </div>
      </div>
    );
};

export default LoadUsers;