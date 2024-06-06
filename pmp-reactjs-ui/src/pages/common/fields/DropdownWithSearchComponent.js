
import { useState, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { handleMouseClickForDropdown } from '../../../utils/AppUtils';

function DropdownWithSearchComponent({ fieldName, dropdownDataList, onDropDownChangeEvent, fieldNameKey, 
    placeHolderKey, searchKey, selectedDropdownValue, styleSet}) {

    const { t } = useTranslation();

    const [selectedDropdownEntry, setSelectedDropdownEntry] = useState("");
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [searchItem, setSearchItem] = useState("");
    const dropdownRef = useRef(null);

    const containsAsterisk = fieldNameKey.includes('*');
    fieldNameKey = containsAsterisk ? fieldNameKey.replace('*', '') : fieldNameKey;

    const filteredPolicyGroupList = dropdownDataList.filter(dropdownItem =>
        dropdownItem.fieldCode.toLowerCase().includes(searchItem.toLowerCase())
    );

    useEffect(() => {
        const clickOutSideDropdown = handleMouseClickForDropdown(dropdownRef, () => setIsDropdownOpen(false));
        return clickOutSideDropdown;
    }, [dropdownRef]);

    useEffect(() => {
        setSelectedDropdownEntry(selectedDropdownValue || "");
    }, [selectedDropdownValue]);

    const changeDropdownSelection = (selectedid) => {
        setSelectedDropdownEntry(selectedid);
        setIsDropdownOpen(false);
        onDropDownChangeEvent(fieldName, selectedid);
    };
    const openDropdown = () => {
        setSearchItem("");
        setIsDropdownOpen(!isDropdownOpen);
    };

    return (
        <div key={fieldName} className={`ml-4 mb-2 ${(styleSet && styleSet.outerDiv) ? styleSet.outerDiv : ''}`}>
            <label className={`block text-dark-blue font-semibold text-sm mb-2 ${(styleSet && styleSet.dropdownLabel) ? styleSet.dropdownLabel : ''}`}>
                {t(fieldNameKey)}{containsAsterisk ? <span className="text-crimson-red">*</span> : ":"}
            </label>
            <div className="relative w-full" ref={dropdownRef}>
                <button onClick={openDropdown} className={`flex items-center justify-between w-[282px] h-10 px-2 py-2 border border-[#707070] bg-white rounded-[4px] text-[15px] text-[#343434] leading-tight focus:outline-none 
                    focus:shadow-none overflow-x-auto whitespace-nowrap no-scrollbar ${(styleSet && styleSet.dropdownButton) ? styleSet.dropdownButton : ''}`} type="button">
                    <span>{
                        selectedDropdownEntry ?
                        dropdownDataList.map(dropdownItem => { return (selectedDropdownEntry === dropdownItem.fieldValue ? dropdownItem.fieldCode : '') })
                        : t(placeHolderKey)}
                    </span>
                    <svg className={`w-3 h-2 ml-3 transform ${isDropdownOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 text-sm`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                    </svg>
                </button>
                {isDropdownOpen && (
                    <div className={`absolute z-50 top-10 left-0 w-full ${(styleSet && styleSet.selectionBox) ? styleSet.selectionBox : ''}`}>
                        <div className="z-10 border border-gray-400 bg-white rounded-md shadow-lg w-full dark:bg-gray-700 cursor-pointer">
                            <div className="p-2 border-b border-gray-200 shadow-sm relative">
                                <span className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-4 text-black mx-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 18a8 8 0 100-16 8 8 0 000 16zM21 21l-5.2-5.2" />
                                    </svg>
                                </span>
                                <input type="text" placeholder={t(searchKey)} value={searchItem} onChange={(e) => setSearchItem(e.target.value)}
                                    className="w-full h-8 pl-8 py-1 text-base text-gray-300 border border-gray-400 rounded-md focus:outline-none focus:text focus:text-gray-800" />
                            </div>
                            <div className="max-h-32 overflow-y-auto">
                                {filteredPolicyGroupList.map((dropdownItem, index) => {
                                    return (
                                        <div key={index} className="min-h-3">
                                            <button
                                                className={`block ${dropdownItem.fieldDescription ? 'h-20' : 'h-8'} w-full px-4 py-1 text-left text-base text-dark-blue
                                                    ${selectedDropdownEntry === dropdownItem.fieldCode ? 'bg-gray-100' : 'hover:bg-gray-100'}`}
                                                onClick={() => changeDropdownSelection(dropdownItem.fieldValue)}>
                                                <span className={`${dropdownItem.fieldDescription ? 'font-semibold' : 'font-normal'}`}>{dropdownItem.fieldCode}</span>
                                                {dropdownItem.fieldDescription && (
                                                    <>
                                                        <br />
                                                        <p className="text-sm text-[#727272]">{dropdownItem.fieldDescription}</p>
                                                    </>
                                                )}
                                            </button>
                                            <div className="border-gray-200 border-t mx-2"></div>
                                        </div>
                                    )
                                })}
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default DropdownWithSearchComponent;