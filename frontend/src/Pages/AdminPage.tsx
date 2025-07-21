import './Common.css';
import { useNavigate } from 'react-router-dom';
import { JSX, useEffect, useRef, useState } from 'react';
import { getKc } from '../Services/KeyCloakService';


import {Card, Divider, Grid, BottomNavigationAction, BottomNavigation, Typography, Select, MenuItem, Button, Chip, TextField} from '@mui/material';

import { Chair, FoodBank } from '@mui/icons-material';
import { FalseLiteral } from 'typescript';
import { DatePicker, DateTimePicker, DesktopDateTimePicker, StaticDateTimePicker, StaticTimePicker, TimePicker } from '@mui/x-date-pickers';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs, { Dayjs } from 'dayjs';
import { PickerValue } from '@mui/x-date-pickers/internals';
import interiorImg from '../assets/images/interior.jpg';
import food1Img from '../assets/images/food1.jpg';
import {ItemData} from '../CustomTypes/TableAndMealItemData';
import axios from 'axios';
import { initCheckSso } from '../Services/KeyCloakService';
import React from 'react';

const AdminPage: React.FC = () => {
  const navigate = useNavigate();
  const [roles, setRoles] = useState<string[] | null>(null);
  useEffect(() => {
      getKc()
      .then(kc => {
          if (!kc.authenticated) {
              // no session → send back to login
              kc.login();
              return;
          }
          // we have a live session: kc.tokenParsed is populated
          
          const newUser = kc.tokenParsed
          setRoles(newUser?.realm_access?.roles);
          console.log("Roles:", roles);
          console.log("Email:", newUser?.email);
          console.log("firstName:", newUser?.firstName);
          console.log("lastName:", newUser?.lastName);
          console.log("phone_number:", newUser?.phone_number);
          console.log("user detected");
          console.log(newUser);
          
      })
      .catch(console.error);
  }, []);
  
  if(roles===null){
      return <div>Loading...</div>;
  }else if(!roles.includes('admin')){
      return <div>Unauthorized</div>;
  }else{
      return <AdminPageContent />;
  }
}
export default AdminPage

const AdminPageContent: React.FC = () => {

  const [showReservations, setShowReservations] = useState<false | true>(true);
    const [value, setValue] = React.useState(0);

    return (
    
        <div className="container">
        <div className="bg bg1" />
        <div className="bg bg2" />
        <div className="bg bg3" /> 
        <Card sx={{width: '100vw', height: '100vh', textAlign: 'center', borderRadius: 0, backgroundColor: 'rgba(255,255,255,0.7)', zIndex: 1, display: 'flex'}} elevation={4}>

            <Card sx={{width: '100%', height: '85%', backgroundColor: 'rgb(226, 226, 226, 0.7)', borderRadius: 5, overflow: 'visible', marginX: '2%', position: 'relative', top: '50%', transform: 'translateY(-55%)'}}>
                <Typography sx={{backgroundColor: 'rgba(237, 90, 36, 0)', fontSize: '3em',    marginTop: -1}}>
                    {showReservations ? 'Reservations' : 'Add Tables'}
                </Typography>
                <Divider orientation="horizontal" flexItem/>
                <Grid container spacing={3}
                    justifyContent="center"
                    alignItems="flex-start"
                    sx={{ height: '90%', padding: 3}}
                    overflow={'auto'}
                >

                    {
                        showReservations 
                                ? 
                        <ReservationsContent />
                                : 
                        <AddTablesContent />
                    }
                </Grid>
            </Card>

            <BottomNavigation
            showLabels
            value={value}
            onChange={(event, newValue) => {
                setValue(newValue);
            }}
            sx={{width: "30%", position: 'absolute', bottom: '2%', left: '50%', transform: 'translateX(-50%)', borderRadius: 2}}
            >
            <BottomNavigationAction label="Reservations" icon={<Chair />} sx={{borderRadius: 2}} onClick={(event)=>{setShowReservations(true)}}/>
            <BottomNavigationAction label="Add Tables" icon={<FoodBank />} sx={{borderRadius: 2}} onClick={(event)=>{setShowReservations(false)}} />
            </BottomNavigation>
        </Card>
    </div>
    )
}


const AddTablesContent: React.FC = () => {
  
  const [titleValue, setTitleValue] = useState('');
  const [descriptionValue, setDescriptionValue] = useState('');
  const [tableAmountValue, setTableAmountValue] = useState('');
  const [seatCountValue, setSeatCountValue] = useState('');
  const [priceValue, setPriceValue] = useState('');
  const [startedEditingFields, setStartedEditingFields] = useState([false,false,false,false,false]);

  const getTitleError = () => {
    return titleValue === '' || titleValue.length>255;
  };

  
  const getTitleHelperText = () => {
    if(titleValue===''){
      return 'This field is required.';
    }else if(titleValue.length>255){
      return 'The title is too long.';
    }else{
      return '';
    }
  };

  const getDescriptionError = () => {
    return descriptionValue === '' || descriptionValue.length>255;
  };

  
  const getDescriptionHelperText = () => {
    if(descriptionValue===''){
      return 'This field is required.';
    }else if(descriptionValue.length>255){
      return 'The description is too long.';
    }else{
      return '';
    }
  };

  
  const getTableAmountError = () => {
    return tableAmountValue === '' || isNotANumber(tableAmountValue);
  };

  
  const getTableAmountHelperText = () => {
    if(tableAmountValue===''){
      return 'This field is required.';
    }else if(isNotANumber(tableAmountValue)){
      return 'The table amount must be a number.';
    }else{
      return '';
    }
  };

  const getSeatCountError = () => {
    return seatCountValue === '' || isNotANumber(seatCountValue);
  };

  
  const getSeatCountHelperText = () => {
    if(seatCountValue===''){
      return 'This field is required.';
    }else if(isNotANumber(seatCountValue)){
      return 'The seat count must be a number.';
    }else{
      return '';
    }
  };

  const getPriceError = () => {
    return priceValue === '' || isNotANumber(priceValue);
  };

  
  const getPriceHelperText = () => {
    if(priceValue===''){
      return 'This field is required.';
    }else if(isNotANumber(priceValue)){
      return 'The price per hour must be a number.';
    }else{
      return '';
    }
  };

  const isNotANumber = (s:string) => {
    const digits = '0123456789';
    return [...s].some(d=> !digits.includes(d))
  }

return (
    
<Card sx={{width: '40%', height: '100%', textAlign: 'center', backgroundColor: 'rgba(255,255,255,0.85)'}} elevation={4}>
        
          <br />
          <div>
        <Grid container spacing={0}
            justifyContent="center"
            alignItems="center"
            sx={{ height: '500px' , overflow: 'auto'}}
        >
          <Grid size={4} >
            <Chip label="Title" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField value={titleValue} 
            onChange={(e) => {
              setTitleValue(e.target.value); 
              setStartedEditingFields((prev)=>{
                prev[0]=true;
                return prev;
              });
            }} 
            id="title" label="Title" variant="outlined"
            error={getTitleError() && startedEditingFields[0]} helperText={!startedEditingFields[0] ? '' : getTitleHelperText()}/>
          </Grid>
          <Grid size={4} >
            <Chip label="Description" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField multiline rows={2} sx={{width: '60%'}}
            id="description" label="Description" variant="outlined" 
            value={descriptionValue} 
            onChange={(e) => {
              setDescriptionValue(e.target.value); 
              setStartedEditingFields((prev)=>{
                prev[1]=true;
                return prev;
              });
            }} 
            error={getDescriptionError() && startedEditingFields[1]} helperText={!startedEditingFields[1] ? '' : getDescriptionHelperText()}/>
          </Grid>
          <Grid size={4} >
            <Chip label="Amount of Tables" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField id="amount" label="Amount of Tables" variant="outlined" 
            value={tableAmountValue} 
            onChange={(e) => {
              setTableAmountValue(e.target.value); 
              setStartedEditingFields((prev)=>{
                prev[2]=true;
                return prev;
              });
            }} 
            error={getTableAmountError() && startedEditingFields[2]} helperText={!startedEditingFields[2] ? '' : getTableAmountHelperText()}/>
          </Grid>
          <Grid size={4} >
            <Chip label="No. of Seats" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField id="seats" label="No. of Seats" variant="outlined" 
            value={seatCountValue} 
            onChange={(e) => {
              setSeatCountValue(e.target.value); 
              setStartedEditingFields((prev)=>{
                prev[3]=true;
                return prev;
              });
            }} 
            error={getSeatCountError() && startedEditingFields[3]} helperText={!startedEditingFields[3] ? '' : getSeatCountHelperText()}/>
          </Grid>
          <Grid size={4} >
            <Chip label="Price per Hour" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField id="price" label="Price per Hour" variant="outlined" 
            value={priceValue} 
            onChange={(e) => {
              setPriceValue(e.target.value); 
              setStartedEditingFields((prev)=>{
                prev[4]=true;
                return prev;
              });
            }} 
            error={getPriceError() && startedEditingFields[4]} helperText={!startedEditingFields[4] ? '' : getPriceHelperText()}/>
          </Grid>
        </Grid>
        <Button variant="contained" sx={{width: '50%'}} onClick={() => {
              
          const url = 'http://localhost:8080/tables/add';
          const requestBody = {
            title           :   titleValue,
            description     :   descriptionValue,
            amountOfTables  :   parseInt(tableAmountValue,10),
            seatCount       :   parseInt(seatCountValue,10),
            price           :   parseInt(priceValue,10)
          };
          axios.post(url, requestBody)
            .then(response => {
              console.log('Response:', response.data);
              console.log('Successful');
              alert("Successfully added table.");
            })
            .catch(error => {
              console.error('Error:', error);
              console.error('Something went wrong');
            });
        }}>Add Table</Button>
        </div>
        <br/>
      </Card>
)
}


const ReservationsContent: React.FC = () => {
  const [date, setDate] = React.useState(dayjs());

  const [reservationData, setReservationData] = React.useState<JSX.Element[]>([]);

  const hours24: JSX.Element[] = [];
  for (let i = 0; i < 24; i++) {
    hours24.push(
        <Grid size={0.416} sx={{borderLeft: '1px solid rgba(0, 0, 0, 0.2)', fontSize: '1em'}}>

            {i}
            <br></br>
             - 
            <br></br>
            {i+1}
        </Grid>
    );
  }

  const processReservationData = (responseData: any) => {
      const types = responseData.types;
      const totalTableCount = responseData.totalAmount;
      const bookingData = responseData.bookedAmount;

      const tempReservationData: JSX.Element[] = [];
      for(let i=0;i<types.length;++i){
        tempReservationData.push(
          <Grid size={2} sx={{borderLeft: '1px solid rgba(0, 0, 0, 0.2)'}}>
              <br></br>
              {types[i]}
          </Grid>
        );

        for (let j = 0; j < 24; j++) {
          tempReservationData.push(
              <Grid size={0.416} sx={{borderLeft: '1px solid rgba(0, 0, 0, 0.2)', fontSize: '1em'}}>
                  <br></br>
                  { j < bookingData[i].length ? bookingData[i][j] : '0'}
                  /
                  {totalTableCount[i]}
              </Grid>
          );
        }
      }
      setReservationData(tempReservationData);
  };

  useEffect(()=>{
    const url = 'http://localhost:8080/booking/reservations';
          const requestBody = {
            date           :   date
          };
          axios.post(url, requestBody)
            .then(response => {
              console.log('Response:', response.data);
              console.log(response.data.types);
              console.log('Successful');
              processReservationData(response.data);
            })
            .catch(error => {
              console.error('Error:', error);
              console.error('Something went wrong');
            });
  }, [date]);
  
return (
    <>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker 
          label="Select Date" 
          value={date}
          onChange={
              (newDateTimeValue)=>{
                  if(newDateTimeValue!=null){
                      setDate(newDateTimeValue);
                  }
              }
          }
        />
    </LocalizationProvider>
<Card sx={{width: '100%', height: '100%', textAlign: 'center', backgroundColor: 'rgba(255,255,255,0.85)'}} elevation={4}>
        
          <br />
        <Grid container spacing={0}
            justifyContent="center"
            alignItems="center"
            sx={{ height: '10%', borderRight: '1px solid rgba(0, 0, 0, 0.2)' }}
        >
            <Grid size={2} sx={{borderLeft: '1px solid rgba(0, 0, 0, 0.2)'}}>

            Tables\Time
        </Grid>
          {hours24}
        {reservationData}
        </Grid>
        <br/>
      </Card>
      </>
)
}