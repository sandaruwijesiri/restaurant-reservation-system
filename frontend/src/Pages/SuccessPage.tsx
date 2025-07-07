
import './Common.css';
import { useLocation, useNavigate } from 'react-router-dom';
import {Card, Button, TextField, Divider, Grid, Chip, Typography} from '@mui/material';
import { DateTimeField, LocalizationProvider } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import {ItemData} from '../CustomTypes/TableAndMealItemData';
import { useEffect, useState } from 'react';

const SuccessPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { startTime, endTime, tableData, mealData } = location.state || {};
  
  const [tableReservationData, setTableReservationData] = useState<string[][]>([['',''],['','']]);
  const [mealReservationData, setMealReservationData] = useState<string[][]>([['',''],['','']]);

  const setTableReservationStrings = () => {
    let tableReservations:string[][] = [];
    let item:ItemData;
    for(let i=0;i<tableData.length;++i){
      item = tableData[i];
      if(item.selectedIndex!=null && item.selectedIndex!=0){
        tableReservations.push([item.title, item.dropDown[item.selectedIndex].toString()])
      }
    }
    setTableReservationData(tableReservations);
  }

  const setMealReservationStrings = () => {
    let mealReservations:string[][] = [];
    let item:ItemData;
    for(let i=0;i<mealData.length;++i){
      item = mealData[i];
      if(item.selectedIndex!=null && item.selectedIndex!=0){
        mealReservations.push([item.title, item.dropDown[item.selectedIndex].toString()])
      }
    }
    setMealReservationData(mealReservations);
  }

  useEffect(()=>{
    setTableReservationStrings();
    setMealReservationStrings();
  },[]);

  return (
    <div className="container">
       <div className="bg bg1" />
       <div className="bg bg2" />
       <div className="bg bg3" />
      <Card sx={{width: '40%', height: '80%', textAlign: 'center', backgroundColor: 'rgba(255,255,255,0.85)'}} elevation={4}>
        
        <br />
        <Chip label="Success" 
          sx={{backgroundColor: '#55AABB', height: 60, width: '80%', fontSize: '2em', borderRadius: 2}}
        />
        <br />
        <br />
        <Divider orientation="horizontal" flexItem />
        <br/>
        <Grid container spacing={3}
              justifyContent="center"
              alignItems="center"
        >
          <Grid size={6} >
            <Typography>From</Typography>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <TextField
                    label="From"
                    value={startTime}
                />
            </LocalizationProvider>
          </Grid>
          <Grid size={6}>
            <Typography>To</Typography>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <TextField
                    label="To"
                    value={endTime}
                />
            </LocalizationProvider>
          </Grid>
        </Grid>
            <br />
        <Grid 
          container
          overflow={'auto'}
          sx={{height: '55%'}}
        >
          <Grid container spacing={3}
              justifyContent="center"
              alignItems="center"
              sx={{width: '100%', height: '40%' }}
          >
            <Grid size={3}>
              <Typography>{"Tables"}</Typography>
            </Grid>
            <Grid container spacing={3}
              justifyContent="center"
              alignItems="center"
              size={9}>

              {tableReservationData.map((table)=>(
                <>
                  <Grid size={6} sx={{borderLeft: '1px solid rgba(0, 0, 0, 0.2)',}}>
                    <Typography sx={{width: 20, whiteSpace: 'pre-line'}}>{table[0]}</Typography>
                  </Grid>
                  <Grid size={6}>
                    <Typography sx={{whiteSpace: 'pre-line'}}>{table[1]}</Typography>
                  </Grid>
                </>
              ))}

            </Grid>
          </Grid>
          
          <Grid size={12}>
            <br />
            <br />
            <br />
            <Divider orientation="horizontal" flexItem />
            <br />
          </Grid>

          <Grid container spacing={3}
              justifyContent="center"
              alignItems="center"
              sx={{ height: '40%' }}
          >

            <Grid size={3}>
              <Typography>Meals</Typography>
            </Grid>
            <Grid container spacing={3}
              justifyContent="center"
              alignItems="center"
              size={9}>
                
              {mealReservationData.map((meal)=>(
                <>
                  <Grid size={6} sx={{borderLeft: '1px solid rgba(0, 0, 0, 0.2)',}}>
                    <Typography sx={{width: 20, whiteSpace: 'pre-line'}}>{meal[0]}</Typography>
                  </Grid>
                  <Grid size={6}>
                    <Typography sx={{whiteSpace: 'pre-line'}}>{meal[1]}</Typography>
                  </Grid>
                </>
              ))}

            </Grid>
          </Grid>
        </Grid>
            <br />
          <Button variant="contained" sx={{width: '50%'}} onClick={() => navigate(-1)}>OK</Button>
        <br/>
      </Card>
    </div>
  )
}

export default SuccessPage