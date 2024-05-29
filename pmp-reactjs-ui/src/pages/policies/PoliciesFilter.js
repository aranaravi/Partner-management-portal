
import { useState, useEffect } from 'react';
import DropdownComponent from '../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { getPartnerTypeDescription, getStatusCode } from '../../utils/AppUtils.js';

function PoliciesFilter({ filteredPoliciesList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [partnerTypeData, setPartnerTypeData] = useState([]);
    const [policyGroupData, setPolicyGroupData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const getData = (fieldName) => {
            let dataArr = [];
            dataArr.push({
                fieldCode: "",
                fieldValue: ""
            });
            filteredPoliciesList.forEach(item => {
                let alreadyAdded = false;
                dataArr.forEach(item1 => {
                    if (item1.fieldValue === item[fieldName]) {
                        alreadyAdded = true;
                    }
                });
                if (!alreadyAdded) {
                    if (fieldName === "partnerType") {
                        dataArr.push({
                            fieldCode: getPartnerTypeDescription(item[fieldName], t),
                            fieldValue: item[fieldName]
                        });
                    } else if (fieldName === "status") {
                        dataArr.push({
                            fieldCode: getStatusCode(item[fieldName], t),
                            fieldValue: item[fieldName]
                        });
                    } else {
                        dataArr.push({
                            fieldCode: item[fieldName],
                            fieldValue: item[fieldName]
                        });
                    }
                }
            });
            return dataArr;
        }
        const fetchData = async () => {
            setPartnerIdData(getData('partnerId'));
            setPartnerTypeData(getData('partnerType'));
            setPolicyGroupData(getData('policyGroup'));
            setPolicyNameData(getData('policyName'));
            setStatusData(getData('status'));
        };
        fetchData();
    }, [filteredPoliciesList]);


    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        onFilterChange(fieldName, selectedFilter);
    }

    return (
        <>
            <div className="flex w-full p-2 justify-start bg-gray-50 flex-wrap">
                <DropdownComponent fieldName='partnerId' dropdownDataList={partnerIdData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.partnerId' dropDownPlaceHolder='policies.selectPartnerId'></DropdownComponent>
                <DropdownComponent fieldName='partnerType' dropdownDataList={partnerTypeData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.partnerType' dropDownPlaceHolder='policies.selectPartnerType'></DropdownComponent>
                <DropdownComponent fieldName='policyGroup' dropdownDataList={policyGroupData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.policyGroup' dropDownPlaceHolder='policies.selectPolicyGroup'></DropdownComponent>
                <DropdownComponent fieldName='policyName' dropdownDataList={policyNameData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.policyName' dropDownPlaceHolder='policies.selectPolicyName'></DropdownComponent>
                <DropdownComponent fieldName='status' dropdownDataList={statusData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.status' dropDownPlaceHolder='policies.selectStatus'></DropdownComponent>
            </div>
        </>
    );
}

export default PoliciesFilter;