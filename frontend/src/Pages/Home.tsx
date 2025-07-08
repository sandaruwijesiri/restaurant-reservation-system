
import React, { useEffect, useState } from 'react';
import './Common.css';
import { useNavigate } from 'react-router-dom';
import {Card, Button, TextField, Divider, Grid, Chip, Stack, BottomNavigationAction, BottomNavigation, Box, Typography, Select, MenuItem, SelectChangeEvent} from '@mui/material';

import RestoreIcon from '@mui/icons-material/Restore';
import FavoriteIcon from '@mui/icons-material/Favorite';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import { Chair, FoodBank } from '@mui/icons-material';
import { FalseLiteral } from 'typescript';
import { DateTimePicker, DesktopDateTimePicker, StaticDateTimePicker, StaticTimePicker, TimePicker } from '@mui/x-date-pickers';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs, { Dayjs } from 'dayjs';
import { PickerValue } from '@mui/x-date-pickers/internals';
import interiorImg from '../assets/images/interior.jpg';
import food1Img from '../assets/images/food1.jpg';
import {ItemData} from '../CustomTypes/TableAndMealItemData';
import axios from 'axios';

const Home: React.FC = () => {
  const navigate = useNavigate();
  let shouldShow = false;
  const [value, setValue] = React.useState(0);
  const [tableToggle, setTableToggle] = useState<false | true>(true);

  
const tableData: ItemData[] = [];
const mealData: ItemData[] = [];
for(let i=0;i<10;++i){
    tableData.push({title: 'Table Title ' + i, description: 'Table description ' + i, image: interiorImg, dropDown: ['Not Selected', '1','2','3','4'], selectedIndex: 0});
    mealData.push({title: 'Meal Title ' + i, description: 'Meal description ' + i, image: food1Img, dropDown: ['Not Selected', 'small', 'medium', 'medium-large', 'large'], selectedIndex: 0});
}


  const [tablesDropDownStates, setTablesDropDownStates] = useState<ItemData[]>(tableData);
  const [mealsDropDownStates, setMealsDropDownStates] = useState<ItemData[]>(mealData);
  const [startTime, setStartTime] = React.useState(dayjs());//useState<string>('2023-04-17T15:30');
  const [endTime, setEndTime] = React.useState(dayjs());//useState<string>('2023-04-17T15:30');

  const handleDropdownChange = (tableOrMealIndex: number) => (event: SelectChangeEvent<number|null>) => {
    const newSelectedIndex = Number(event.target.value); // convert string to number
    if (tableToggle) {
        setTablesDropDownStates((prev) =>
        prev.map((item, index) =>
            index === tableOrMealIndex
            ? { ...item, selectedIndex: newSelectedIndex }
            : item
        )
        );
    } else {
        setMealsDropDownStates((prev) =>
        prev.map((item, index) =>
            index === tableOrMealIndex
            ? { ...item, selectedIndex: newSelectedIndex }
            : item
        )
        );
    }
  };

  const getAvailableTables = () => {
    const url = 'http://localhost:8080/tables';
    const requestBody = {
        startTime    : startTime,
        endTime      : endTime
    };
    
    let tableAvailabilityResponse:{
        title: string;
        description: string;
        pricePerHour: string;
        imagePath: string;
        seatCount: number;
        availableCount: number;
    }[] = [];

    axios.post(url, requestBody)
        .then(response => {
            tableAvailabilityResponse = response.data;
            setTablesDropDownStates((prev) => {
                let availableTables: ItemData[] = [];
                for(const table of tableAvailabilityResponse){
                    if(table.availableCount>0){
                        let dropDown = ['Not selected'];
                        for(let i=1;i<=table.availableCount;++i){
                            dropDown.push(i.toString());
                        }
                        availableTables.push({
                            title: table.title, 
                            description: table.description + '\n\nNo. of Seats: ' + table.seatCount + '\n\nRs.' + table.pricePerHour + " per Hour", 
                            image: table.imagePath, 
                            dropDown: dropDown,
                            selectedIndex: 0})
                    }
                }
                return availableTables;
            });
            console.log('Response:', response.data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

  const getMeals = () => {
    const url = 'http://localhost:8080/meals';
    
    let mealsResponse:{
        title: string;
        description: string;
        imagePath: string;
        portions: string[];
        prices: string[];
    }[] = [];

    axios.get(url)
        .then(response => {
            mealsResponse = response.data;
            setMealsDropDownStates((prev) => {
                let meals: ItemData[] = [];
                for(const meal of mealsResponse){
                    if(meal.portions.length>0){
                        let dropDown = ['Not selected'];
                        for(let i=0;i<meal.portions.length;++i){
                            dropDown.push(meal.portions[i]);
                        }
                        let descriptionString = meal.description + "\n\nPortions: ";
                        for(let i=1;i<dropDown.length;++i){
                            descriptionString += "\n\t" + dropDown[i] + ": LKR " + meal.prices[i-1];
                        }
                        meals.push({
                            title: meal.title, 
                            description: descriptionString, 
                            image: meal.imagePath, 
                            dropDown: dropDown,
                            selectedIndex: 0})
                    }
                }
                return meals;
            });
            console.log('Response:', response.data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    const addReservation = () => {
        let tablesSelected = false;
        for(let i=0;i<tablesDropDownStates.length;++i){
            if(tablesDropDownStates[i].selectedIndex!=0){
                tablesSelected = true;
                break;
            }
        }
        if(!tablesSelected){
            alert("Please select tables.");
            return;
        }

        const url = 'http://localhost:8080/booking';
        const requestBody = {
                                        startTime: startTime,
                                        endTime: endTime,
                                        tableData: tablesDropDownStates,
                                        mealData: mealsDropDownStates
                                    };
        axios.post(url,requestBody).then(
            response => {
                console.log(response.status);
                console.log("Reservation Successful");
                navigate('/success',
                    {
                        state: {
                            startTime: startTime.format('YYYY/MM/DD hh A'),
                            endTime: endTime.format('YYYY/MM/DD hh A'),
                            tableData: tablesDropDownStates,
                            mealData: mealsDropDownStates
                        }
                    }
                );
            }
        ).catch((error) => {
            if(error.response.status === 409){
                alert("Something went wrong: " + error.response.data);
            }
        });
    }

    useEffect(()=>{
        getAvailableTables();
        getMeals();
    }, []);
    useEffect(()=>{
        if(endTime.isBefore(startTime)){
            setStartTime(endTime);
        }
        getAvailableTables();
    }, [endTime]);
    useEffect(()=>{
        if(endTime.isBefore(startTime)){
            setEndTime(startTime);
        }
        getAvailableTables();
    }, [startTime]);
    

  
  return (
    <div className="container">
        <div className="bg bg1" />
        <div className="bg bg2" />
        <div className="bg bg3" /> 
        <Card sx={{width: '100vw', height: '100vh', textAlign: 'center', borderRadius: 0, backgroundColor: 'rgba(255,255,255,0.7)', zIndex: 1, display: 'flex'}} elevation={4}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Box textAlign={'center'} sx={{width: '20%', padding: 2, backgroundColor: 'rgba(38, 71, 202, 0.5)'}}>
                    <div>
                        <br />
                        <Chip label="From" 
                            sx={{backgroundColor: 'rgba(237, 90, 36, 0.51)', height: 60, width: 'min-content', fontSize: '2em', borderRadius: 2, padding: 5}}
                        />
                        <br />
                        <br /> 
                    </div>
                    
                    <DateTimePicker 
                        value={startTime}
                        views={['year', 'month', 'day', 'hours']} 
                        disablePast={true} 
                        slots={{ actionBar: () => null }}
                        sx={{backgroundColor: 'rgba(255,255,255,0.5)', padding: 2}}
                        onChange={
                            (newDateTimeValue)=>{
                                if(newDateTimeValue!=null){
                                    setStartTime(newDateTimeValue);
                                }
                            }
                        }
                    />
                    <div>
                        <br />
                        <br />
                        <Chip label="To" 
                            sx={{backgroundColor: 'rgba(237, 90, 36, 0.51)', height: 60, width: 'min-content', fontSize: '2em', borderRadius: 2, padding: 5}}
                        />
                        <br />
                        <br />
                    </div>
                    <DateTimePicker
                        value={endTime}
                        views={['year', 'month', 'day', 'hours']}
                        disablePast={true}
                        slots={{ actionBar: () => null }}
                        sx={{backgroundColor: 'rgba(255,255,255,0.5)', padding: 2}}
                        onChange={
                            (newDateTimeValue)=>{
                                if(newDateTimeValue!=null){
                                    setEndTime(newDateTimeValue);
                                }
                            }
                        }
                    />
                    <br />
                    <br />
                    <br />
                    <br />
                    <Button variant="contained" sx={{width: '70%', height: '20%', fontSize: '2em'}} onClick={addReservation}>Reserve</Button>
                    </Box>
            </LocalizationProvider>

            <Card sx={{width: '100%', height: '85%', backgroundColor: 'rgb(226, 226, 226, 0.7)', borderRadius: 5, overflow: 'visible', marginX: '2%', position: 'relative', top: '50%', transform: 'translateY(-55%)'}}>
                <Typography sx={{backgroundColor: 'rgba(237, 90, 36, 0)', fontSize: '3em',    marginTop: -1}}>
                    {tableToggle ? 'Tables' : 'Meals'}
                </Typography>
                <Divider orientation="horizontal" flexItem/>
                <Grid container spacing={3}
                    justifyContent="center"
                    alignItems="flex-start"
                    sx={{ height: '90%', padding: 3}}
                    overflow={'auto'}
                >

                    {(tableToggle ? tablesDropDownStates : mealsDropDownStates).map((tableOrMealValue, tableOrMealIndex) => (
                        <Grid size={4} position={'relative'}>
                            <Typography fontSize={'2em'}>{tableOrMealValue.title}</Typography>
                            <img src={tableOrMealValue.image} alt="Interior" style={{ width: 300, height: 300 }} />
                            <Typography sx={{width: 300, whiteSpace: 'pre-line'}}>{tableOrMealValue.description}</Typography>
                            <Select<number|null>
                                labelId={"demo-simple-select-label"+tableOrMealIndex}
                                id={"demo-simple-select"+tableOrMealIndex}
                                value={tableOrMealValue.selectedIndex}
                                label={tableToggle ? 'Amount' : 'Portion'}
                                sx={{width: '50%'}}
                                onChange={handleDropdownChange(tableOrMealIndex)}
                            >
                                {tableOrMealValue.dropDown.map((dropDownValue, dropDownIndex) => (
                                    <MenuItem key={dropDownIndex} value={dropDownIndex}>{tableOrMealValue.dropDown[dropDownIndex]}</MenuItem>
                                ))}
                            </Select>
                        </Grid>
                    ))}
                </Grid>
            </Card>

            <BottomNavigation
            showLabels
            value={value}
            onChange={(event, newValue) => {
                setValue(newValue);
            }}
            sx={{width: "30%", position: 'absolute', bottom: '2%', left: '60%', transform: 'translateX(-50%)', borderRadius: 2}}
            >
            <BottomNavigationAction label="Tables" icon={<Chair />} sx={{borderRadius: 2}} onClick={(event)=>{setTableToggle(true)}}/>
            <BottomNavigationAction label="Meals" icon={<FoodBank />} sx={{borderRadius: 2}} onClick={(event)=>{setTableToggle(false)}} />
            </BottomNavigation>
        </Card>
    </div>
  )
}

export default Home