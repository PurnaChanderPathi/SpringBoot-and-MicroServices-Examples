import React from 'react'
import './HomePage.css'
import { Button } from '@mui/material'
import CachedIcon from '@mui/icons-material/Cached';
import BasicTabs from './Tabs';

const HomePage = () => {
  return (
    <div className='MainDiv'>
      <div className='AdminScreen'>
        <button className='AdminButton'>Admin Screen</button>
      </div>
      <div className='NewReview'>
      <button className='AdminButton'>Initiate New Review</button>
      <button className='AdminButton'>Re-Assign</button>
      </div>
      <div className='Reload'>
        <CachedIcon className='ReloadIcon'/>
      </div>
      <div className='tabs'>
        <BasicTabs/>
      </div>
    </div>
  )
}

export default HomePage
