
import './Common.css';
import { useNavigate } from 'react-router-dom';
import {Card, Button, TextField, Divider, Grid, Chip} from '@mui/material';
import axios from 'axios';
import { useRef } from 'react';

const Registration: React.FC = () => {
  const navigate = useNavigate();

  const firstNameRef = useRef<HTMLInputElement>(null);
  const lastNameRef = useRef<HTMLInputElement>(null);
  const nicRef = useRef<HTMLInputElement>(null);
  const phoneNumRef = useRef<HTMLInputElement>(null);
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const cpasswordRef = useRef<HTMLInputElement>(null);



  return (
    <div className="container">
       <div className="bg bg1" />
       <div className="bg bg2" />
       <div className="bg bg3" />
      <Card sx={{width: '40%', textAlign: 'center', backgroundColor: 'rgba(255,255,255,0.85)'}} elevation={4}>
        
          <br />
            <Chip label="Please enter your details below." 
            sx={{backgroundColor: '#55AABB', height: 60, width: '80%', fontSize: '2em', borderRadius: 2}}
            />
            <br />
            <br />
        <Divider orientation="horizontal" flexItem />
        <br/>
        <Grid container spacing={3}
            justifyContent="center"
            alignItems="center"
            sx={{ height: '10%' }}
        >
          <Grid size={4} >
            <Chip label="First Name" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={firstNameRef} id="fName" label="First Name" variant="outlined" />
          </Grid>
          <Grid size={4} >
            <Chip label="Last Name" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={lastNameRef} id="lName" label="Last Name" variant="outlined" />
          </Grid>
          <Grid size={4} >
            <Chip label="NIC Number" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={nicRef} id="nic" label="NIC Number" variant="outlined" />
          </Grid>
          <Grid size={4} >
            <Chip label="Phone Number" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={phoneNumRef} id="phone" label="Phone Number" variant="outlined" />
          </Grid>
          <Grid size={4} >
            <Chip label="Email Address" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={emailRef} id="email" label="Email Address" variant="outlined" />
          </Grid>
          <Grid size={4} >
            <Chip label="Password" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={passwordRef} id="password" label="Password" variant="outlined" />
          </Grid>
          <Grid size={4} >
            <Chip label="Confirm Password" sx={{backgroundColor: '#55AABB', height: 40, width: '90%', fontSize: '1em', borderRadius: 2}}/>
          </Grid>
          <Grid size={8}>
            <TextField inputRef={cpasswordRef} id="cPassword" label="Confirm Password" variant="outlined" />
          </Grid>
        <Button variant="contained" sx={{width: '50%'}} onClick={() => {
            navigate('/home');
            const url = 'http://localhost:8080/customers/register';

            const requestBody = {
              firstName     : firstNameRef.current?.value,
              lastName      : lastNameRef.current?.value,
              nic           : nicRef.current?.value,
              phoneNumber   : phoneNumRef.current?.value,
              email         : emailRef.current?.value,
              password      : passwordRef.current?.value
            };

            axios.post(url, requestBody)
              .then(response => {
                console.log('Response:', response.data);
              })
              .catch(error => {
                console.error('Error:', error);
              });
          }
          }>
            Register
          </Button>
        </Grid>
        <br/>
      </Card>
    </div>
  )
}

export default Registration