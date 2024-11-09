import React, { useEffect, useState } from 'react'
import './CaseInformation.css'
import { useParams } from 'react-router-dom';
import PlanningStage from './PlanningStage';
import PlanningTabs from './PlanningStage';

const CaseInformation = () => {
    const {reviewId} = useParams();
    const [caseData,setCaseData] = useState( {
        reviewId: '',
        groupName: '',
        division: '',
        role: '',
        assignedToUser: '',
    })
    const [loading, setLoading] = useState(true);

      // Fetch data when the component mounts
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:9193/api/query/${reviewId}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setCaseData({
          reviewId: data.reviewId,
          groupName: data.groupName,
          division: data.division,
          role: data.role,
          assignedToUser: data.assignedToUser
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false); // Stop loading
      }
    };

    fetchData(); // Call the fetch function
  }, [reviewId]); // Re-run when reviewId changes

  // If data is still loading, show a loading message
  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className='CaseInfoMainDiv'>
      <div className='CaseInfoScreen'>
            <div className='CaseInfoheading'>
                Case Information
            </div>
      </div>
      <div className='fetchCaseInformation'>
            <div className='ReviewIdinfo'>
                <div className='ReviewLabel'>
                ReviewId
                </div>
                <div className='ReviewInputCase'>
                <input type='text' value={caseData.reviewId}
                 className='inputReview'
                  disabled/>
                </div>
 
            </div>
            <div className='ReviewIdinfo'>
            <div className='ReviewLabel'>
                GroupName
                </div>
                <div className='ReviewInputCase'>
                <input type='text'
                value={caseData.groupName}
                 className='inputReview'
                  disabled />
                </div>
            </div>
            <div className='ReviewIdinfo'>
            <div className='ReviewLabel'>
                Division
                </div>
                <div className='ReviewInputCase'>
                <input type='text'
                value={caseData.division}
                className='inputReview'
                 disabled />
                </div>
            </div>
            <div className='ReviewIdinfo'>
            <div className='ReviewLabel'>
                Role
                </div>
                <div className='ReviewInputCase'>
                <input type='text'
                value={caseData.role}
                 className='inputReview' disabled />
                </div>
            </div>
            <div className='ReviewIdinfo'>
            <div className='ReviewLabel'>
                PPC Initiator
                </div>
                <div className='ReviewInputCase'>
                <input type='text'
                value={caseData.assignedToUser} className='inputReview' disabled />
                </div>
            </div>
      </div>
      <PlanningTabs />
    </div>
  )
}

export default CaseInformation
