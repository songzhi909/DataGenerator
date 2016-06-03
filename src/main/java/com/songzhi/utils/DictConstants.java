package com.songzhi.utils;

/***
 * 字典常量 格式： 类型#内容
 *  <ul>
 *  	<li>1)类型： R：关联数据, F:文件字典,D:数据字典, E:集合数据</li>
 *  	<li>2)当为R时,内容 = 关联表,关联字段</li>
 *  	<li>3）当为D时,内容 = 取值类型&字典名称,取值类型为0：键与值,1：键,2：值 </li>
 *  	<li>4)当为E时,内容 = 集合数据，已','分隔</li>
 *  </ul>
 */
public class DictConstants {
	
	/*
	 * -----------------------------------------------------------------------------------------------
	 * ------------------------------------------关联字段 R---------------------------------------------
	 * -----------------------------------------------------------------------------------------------
	 */
	/**业务域ID */
	public static String AUTHORITY_ID 	= "R#PAT_MASTER_INDEX,AUTHORITY_ID";
	public static String PID 						= "R#PAT_MASTER_INDEX,PID";
	public static String TENANT_ID 			= "R#PAT_MASTER_INDEX,TENANT_ID";
	public static String NAME 					= "R#PAT_MASTER_INDEX,NAME";
	public static String OUTP_CLINIC_ID = "R#CLINIC_MASTER,OUTP_CLINIC_ID";
	public static String INP_CLINIC_ID	= "R#PATS_IN_HOSPITAL,INP_CLINIC_ID";
	/** 病案号 */
	public static String MR_NO					= "R#PATS_IN_HOSPITAL,MR_NO";
	/** 中西医病案首页标示 */
	public static String MR_FLAG				= "R#PAT_VISIT,MR_FLAG";
	/** 门诊处方号 */
	public static String PRESCRIPTION_ID	= "R#CLINIC_PRES_MASTER,PRESCRIPTION_ID";
///** 住院收费 收据号*/
//public static String RECEIPT_NUMBER	= "R#INP_BILL_MASTER,RECEIPT_NUMBER";
	/** 门诊收费 收据号*/
	public static String RECEIPT_NUMBER	= "R#OUTP_BILL_MASTER,RECEIPT_NUMBER";
	/** 住院医嘱ID */
	public static String MEDICAL_ADVICE_ID	= "R#INPATIENT_ORDERS,MEDICAL_ADVICE_ID";
	/** 住院次数 */
	public static String ADMISSION_NUMBER	= "R#PATS_IN_HOSPITAL,ADMISSION_NUMBER";
	/** 检验ID */
	public static String LAB_ID	= "R#LAB_RECORD,LAB_ID";
	/** 检查ID*/
	public static String EXAMINATION_ID	= "R#EXAM_RECORD,EXAMINATION_ID";
	/** 手术记录ID */
	public static String OPERATION_ID = "R#INPATIENT_OPERATION,OPERATION_ID";
	
	
	
	
	

	/*
	 * -----------------------------------------------------------------------------------------------
	 * ------------------------------------------集合数据字段 E---------------------------------------------
	 * -----------------------------------------------------------------------------------------------
	 */
	/**医嘱类别代码 */
	public static String MEDICAL_ADVICE_TYPE_CODE 	= "E#1&1=长期,2=临时";
	/**医嘱类别名称 */
	public static String MEDICAL_ADVICE_TYPE_NAME 	= "E#2&1=长期,2=临时";
	/**医嘱类别代码 */
	public static String MEDICAL_ADVICE_TYPE 	= "E#1&1=长期,2=临时";
	/** 实施临床路径代码 */
	public static String IMPLEMENT_CLINICAL_PATH_CODE = "E#1&1=中医,2=西医,3=否";
	/** 实施临床路径 */
	public static String IMPLEMENT_CLINICAL_PATH_NAME = "E#2&1=中医,2=西医,3=否";
	/** 药物使用-频率编码 */
	public static String DRUG_USE_FREQUENCY_CODE = "E#1&1=1/日,2=2/日,3=3/日,4=4/日";
	/** 药物使用-频率 */
	public static String DRUG_USE_FREQUENCY = "E#2&1=1/日,2=2/日,3=3/日,4=4/日";
	/** 入院途径代码 */
	public static String ADMISSION_PATHWAYS_CODE = "E#1&1=门诊,2=病房,3=外来,4=其他,5=急诊";
	/** 入院途径名称 */
	public static String ADMISSION_PATHWAYS_NAME = "E#2&1=门诊,2=病房,3=外来,4=其他,5=急诊";
//	/**中西医病案首页代码 */
//	public static String MR_FLAG 	= "E#1&1=中医病案首页,2=西医病案首页";
	/** 医嘱项目类型代码  */
	public static String MEDICAL_ITEM_CLASS_CODE = "E#1&1=药疗,2=处置,3=检查,4=化验,5=手术,6=护理,7=膳食,9=其他";
	/** 医嘱项目类型名称 */
	public static String MEDICAL_ITEM_CLASS_NAME = "E#2&1=药疗,2=处置,3=检查,4=化验,5=手术,6=护理,7=膳食,9=其他";

	
	/*
	 * -----------------------------------------------------------------------------------------------
	 * ------------------------------------------文件字典字段 F-----------------------------------------
	 * -----------------------------------------------------------------------------------------------
	 */
	/**身份标识-类别代码*/
	public static String ID_TYPE_CODE = "F#2&COMM_CONFIG_ID_TYPE";
	/** 身份标识-类别描述*/
	public static String ID_TYPE_NAME = "F#1&COMM_CONFIG_ID_TYPE";
	/** 性别代码*/
	public static String SEX_CODE = "F#2&COMM_CONFIG_SEX";
	/** 性别名称 */
	public static String SEX_NAME = "F#1&COMM_CONFIG_SEX";
	/** 国籍代码 */
	public static String NATIONALITY_CODE = "F#2&COMM_CONFIG_COUNTRY";
	/** 国籍名称 */
	public static String NATIONALITY_NAME = "F#1&COMM_CONFIG_COUNTRY";
	/** 民族代码 */
	public static String NATION_CODE = "F#2&COMM_CONFIG_NATIONALITY";
	/** 民族名称 */
	public static String NATION_NAME = "F#1&COMM_CONFIG_NATIONALITY";
	/** 血型代码 */
	public static String BLOOD_TYPE_CODE = "F#2&COMM_CONFIG_ABO";
	/** 血型名称 */
	public static String BLOOD_TYPE_NAME = "F#1&COMM_CONFIG_ABO";
	/** RH血型代码 */
	public static String RH_BLOOD_TYPE_CODE = "F#2&COMM_CONFIG_RH";
	/** RH血型 */
	public static String RH_BLOOD_TYPE_NAME = "F#1&COMM_CONFIG_RH";
	/** 婚姻状况类别代码 */
	public static String MARITAL_STATUS_CODE = "F#2&COMM_CONFIG_MARITAL_STATUS";
	/** 婚姻状况类别名称 */
	public static String MARITAL_STATUS_NAME = "F#1&COMM_CONFIG_MARITAL_STATUS";
	/** 职业类别代码 */
	public static String OCCUPATIONAL_CATEGORY_CODE = "F#2&COMM_CONFIG_VOCATION";
	/** 职业类别名称 */
	public static String OCCUPATIONAL_CATEGORY_NAME = "F#1&COMM_CONFIG_VOCATION";
	/** 文化程度代码 */
	public static String EDUCATION_LEVEL_CODE = "F#2&COMM_CONFIG_DEGREE_LEVEL";
	/** 文化程度名称 */
	public static String EDUCATION_LEVEL_NAME = "F#1&COMM_CONFIG_DEGREE_LEVEL";
	/** 联系人关系 */
	public static String RELATIONSHIP_WITH_CONTACT = "F#2&COMM_CONFIG_RELATIONSHIP";
	/** 学历代码 */
	public static String COMM_CONFIG_DEGREE_ID = "F#2&COMM_CONFIG_DEGREE";
	/** 学历描述 */
	public static String COMM_CONFIG_DEGREE_NAME = "F#1&COMM_CONFIG_DEGREE";
	/** ABO血型代码  */
	public static String ABO_BLOOD = "F#2&COMM_CONFIG_ABO";
	/** ABO血型名称 */
	public static String BLOOD = "F#2&COMM_CONFIG_ABO";
	/** RH血型代码 */
	public static String RH_BLOOD_CODE = "F#2&COMM_CONFIG_RH";
	/** RH血型 */
	public static String RH_BLOOD_NAME = "F#1&COMM_CONFIG_RH";
	/** 职业代码 */
	public static String PROFESSION_CODE = "F#2&COMM_CONFIG_VOCATION";
	/** 职业描述 */
	public static String PROFESSION_NAME = "F#1&COMM_CONFIG_VOCATION";
	/** 婚姻 */
	public static String MARRIAGE_CODE = "F#2&COMM_CONFIG_MARITAL_STATUS";
	/** 婚姻状况 */
	public static String MARRIAGE_STATUS = "F#2&COMM_CONFIG_MARITAL_STATUS";
	
	
	
	/**医疗保险-类别代码*/
	public static String MEDICAL_INSURANCE_CODE	=	"F#2&COMM_CONFIG_INSURANCE_TYPE";
	/**医疗保险-类别名称*/
	public static String MEDICAL_INSURANCE_NAME	=	"F#1&COMM_CONFIG_INSURANCE_TYPE";
	
//	/**医疗待遇代码 */
//	public static String MEDICAL_TREATMENT_CODE = "";
//	/**医疗待遇名称 */
//	public static String MEDICAL_TREATMENT_NAME	=	"";
	
	/** 科室代码 */
	public static String DEPT_CODE = "F#2&COMM_CONFIG_DEPT";
	/** 科室名称 */
	public static String DEPT_NAME = "F#1&COMM_CONFIG_DEPT";
	/** 申请科室名称 */
	public static String APPLICATION_DEPARTMENT_NAME = "F#1&COMM_CONFIG_DEPT";
	/** 申请科室代码 */
	public static String APPLICATION_DEPARTMENT_CODE = "F#2&COMM_CONFIG_DEPT";
	
	
	
	
	/** 中医证侯代码 */
	public static String TCM_SYNDROMES_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 中医证侯名称 */
	public static String TCM_SYNDROMES_NAME = "F#1&COMM_CONFIG_ICD10";
	/** 中医“四诊”观察结果  */
	public static String TCM_4_DIAGNOS_RESULT = "F#1&COMM_CONFIG_ICD10";
	
	/** 中药煎煮法代码  */
	public static String TCM_DECOCTION_CODE = "F#1&TCM_DECOCTION";
	/** 中药煎煮法名称 */
	public static String TCM_DECOCTION_NAME = "F#2&TCM_DECOCTION";
	/** 药物名称 */
	public static String DRUG_NAME = "F#1&DRUG";
	/** 药物代码 */
	public static String DRUG_CODE = "F#2&DRUG";
	/** 药品规格 */
	public static String DRUG_SPECIFICATION = "F#2&DRUG_SPECIFICATION";
//	/** 药物使用-次剂量 */
//	public static String SINGLE_DOSE = "E#2&COMM_CONFIG_SEX";
//	/** 药物使用-剂量单位 */
//	public static String DOSAGE_UNIT = "F#2&COMM_CONFIG_SEX";
//	/** 药物使用-总剂量 */
//	public static String TOTAL_DOSAGE = "F#2&COMM_CONFIG_SEX";
	/** 症状描述 */
	public static String SYMPTOM_DESC = "F#1&COMM_CONFIG_ICD10";
	/** 入院科室代码 */
	public static String HOSPITAL_DEPARTMENTS_CODE = "F#2&COMM_CONFIG_DEPT";
	/** 入院科室名称 */
	public static String HOSPITAL_DEPARTMENTS_NAME = "F#1&COMM_CONFIG_DEPT";
	/** 主诉  */
	public static String CHIEF_COMPLAINT = "E#2&1=持续发热6天，全身红色斑丘诊3天,2=发热、流涕、咽痛、咳嗽2天,3=多饮、多食、多尿、消瘦5月,4=瘀点、瘀斑、头晕1月,5=劳累后心悸、气急、浮肿反复发作5年余,6=尿频、尿急3小时";
	
	/** 出院科室名称 */
	public static String DISCHARGED_DEPARTMENT_NAME = "F#1&COMM_CONFIG_DEPT";
	/** 出院科室代码 */
	public static String DISCHARGED_DEPARTMENT_CODE = "F#2&COMM_CONFIG_DEPT";
	
	/** 入院诊断-代码 */
	public static String ADMISSION_DIAGNOSIS_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 入院诊断-名称 */
	public static String ADMISSION_DIAGNOSIS_NAME = "F#1&COMM_CONFIG_ICD10";
	/** 主要诊断代码 */
	public static String MAJOR_DIAGNOSTIC_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 主要诊断描述 */
	public static String MAJOR_DIAGNOSTIC_DESCRIPTION = "F#2&COMM_CONFIG_ICD10";
	/** 其他诊断代码 */
	public static String OTHER_DIAGNOSTIC_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 其他诊断描述 */
	public static String OTHER_DIAGNOSTIC_DESCRIPTION = "F#2&COMM_CONFIG_ICD10";
	/** 疾病诊断名称 */
	public static String NAME_OF_DISEASE_DIAGNOSIS = "F#2&COMM_CONFIG_ICD10";
	/** 疾病诊断代码 */
	public static String DISEASE_DIAGNOSIS_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 门（急）诊诊断（西医诊断）  */
	public static String W_OUTPATIENT_DIAGNOSTIC_NAME = "F#1&COMM_CONFIG_ICD10";
	/** 门（急）诊诊断（西医诊断）疾病编码 */
	public static String W_OUTPATIENT_DIAGNOSTIC_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 门（急）诊诊断（中医诊断） */
	public static String C_OUTPATIENT_DIAGNOSTIC_NAME = "F#1&COMM_CONFIG_ICD10";
	/** 门（急）诊诊断（中医诊断）疾病编码 */
	public static String C_OUTPATIENT_DIAGNOSTIC_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 出院中医主病疾病编码 */
	public static String TCM_HOST_DISEASE_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 出院中医主病 */
	public static String TCM_HOST_DISEASE_NAME = "F#1&COMM_CONFIG_ICD10";

	/**出院西医主要诊断编码  */
	public static String W_MAIN_DIAGNOSTIC_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 出院西医主要诊断 */
	public static String W_MAIN_DIAGNOSTIC_NAME = "F#1&COMM_CONFIG_ICD10";
	/** 住院患者损伤和中毒外部原因 */
	public static String INJURY_POISONING_NAME = "F#1&COMM_CONFIG_ICD10";
	/** 损伤、中毒疾病编码 */
	public static String INJURY_POISONING_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 病理诊断疾病编码 */
	public static String PATHOLOGICAL_DIAG_DISEASE_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 病理诊断 */
	public static String PATHOLOGICAL_DIAGNOSIS_NAME = "F#1&COMM_CONFIG_ICD10";
	/**出院中医主证编码  */
	public static String TCM_MAIN_SYMPTOMS_CODE = "F#2&COMM_CONFIG_ICD10";
	/** 出院中医主证  */
	public static String TCM_MAIN_SYMPTOMS_NAME = "F#1&COMM_CONFIG_ICD10";
	
	/*
	 *行政区信息 
	 */
	/**(出生地)地址-省(自治区、直辖市)*/
	public static String PROVINCE_OF_BIRTHPLACE = "F#1&COMM_CONFIG_LOCATION";
	/**(出生地)地址-市(地区) */
	public static String CITY_OF_BIRTHPLACE = "F#1&COMM_CONFIG_LOCATION";
	/** (出生地)地址-县(区)*/
	public static String COUNTY_OF_BIRTHPLACE = "F#1&COMM_CONFIG_LOCATION";
	/**(出生地)地址-乡(镇、街道办事处) */
	public static String TOWN_OF_BIRTHPLACE = "F#1&COMM_CONFIG_LOCATION";
	/**(居住地)地址-行政区划代码 */
	public static String DIVISIONS_CODE_OF_RESIDENCE = "F#2&COMM_CONFIG_LOCATION";
	/** (居住地)地址-省(自治区、直辖市)*/
	public static String PROVINCE_OF_RESIDENCE = "F#1&COMM_CONFIG_LOCATION";
	/** (居住地)地址-市(地区)*/
	public static String CITY_OF_RESIDENCE = "F#1&COMM_CONFIG_LOCATION";
	/** (居住地)地址-县(区)*/
	public static String COUNTY_OF_RESIDENCE = "F#1&COMM_CONFIG_LOCATION";
	/** (居住地)地址-乡(镇、街道办事处)*/
	public static String TOWN_OF_RESIDENCE = "F#1&COMM_CONFIG_LOCATION";
	/** (居住地)地址-村(街、路、弄等)*/
	public static String VILLAGE_OF_RESIDENCE = "F#1&COMM_CONFIG_LOCATION_VILLAGE";
	
	/** 出生地 省（自治区、直辖市） */
	public static String BIRTHPLACE_PROVINCE = "F#1&COMM_CONFIG_LOCATION";
	/** 出生地 市（地区） */
	public static String BIRTHPLACE_CITY = "F#1&COMM_CONFIG_LOCATION";
	/** 出生地 县（区） */
	public static String BIRTHPLACE_COUNTY = "F#1&COMM_CONFIG_LOCATION";
	/** 籍贯 省（自治区、直辖市） */
	public static String HOMETOWN_PROVINCE = "F#1&COMM_CONFIG_LOCATION";
	/** 籍贯 市（地区） */
	public static String HOMETOWN_CITY = "F#1&COMM_CONFIG_LOCATION";
	/** 籍贯 县 */
	public static String HOMETOWN_COUNTY = "F#1&COMM_CONFIG_LOCATION";
	/** 现住址 省（自治区、直辖市） */
	public static String CURRENT_ADRESS_PROVINCE = "F#1&COMM_CONFIG_LOCATION";
	/** 现住址 市（地区） */
	public static String CURRENT_ADRESS_CITY = "F#1&COMM_CONFIG_LOCATION";
	/** 现住址 县（区） */
	public static String CURRENT_ADRESS_COUNTY = "F#1&COMM_CONFIG_LOCATION";
	/** 户口地址 省（自治区、直辖市） */
	public static String ACCOUNT_ADRESS_PROVINCE = "F#1&COMM_CONFIG_LOCATION";
	/** 户口地址 市（地区） */
	public static String ACCOUNT_ADRESS_CITY = "F#1&COMM_CONFIG_LOCATION";
	/** 户口地址 县（区） */
	public static String ACCOUNT_ADRESS_COUNTY = "F#1&COMM_CONFIG_LOCATION";

	/**联系人地址-省(自治区、直辖市) */
	public static String PROVINCE_WITH_CONTACT = "F#1&COMM_CONFIG_LOCATION";
	/**联系人地址-市(地区) */
	public static String CITY_WITH_CONTACT = "F#1&COMM_CONFIG_LOCATION";
	/**联系人地址-县(区) */
	public static String COUNTY_WITH_CONTACT = "F#1&COMM_CONFIG_LOCATION";
	/**联系人地址-乡(镇、街道办事处) */
	public static String TOWN_WITH_CONTACT = "F#1&COMM_CONFIG_LOCATION";
	/**联系人地址-村(街、路、弄等) */
	public static String VILLAGE_WITH_CONTACT = "F#1&COMM_CONFIG_LOCATION";
	
	
	
	/**药物过敏标志代码 */
	public static String DRUG_ALLERGY_SIGN_CODE = "F#2&COMM_CONFIG_HAVE";
	/** 药物过敏标志 */
	public static String DRUG_ALLERGY_SIGN_NAME = "F#1&COMM_CONFIG_HAVE";
	/** 有无出院31天内再住院计划代码 */
	public static String READMISSIONS_31DAY_CODE = "F#2&COMM_CONFIG_HAVE";
	/** 有无出院31天内再住院计划*/
	public static String READMISSIONS_31DAY_NAME = "F#1&COMM_CONFIG_HAVE";
	
	// TODO： 7.5.3.7	住院病案首页-中医诊断主证 PAT_VISIT_SYSMPTOM 
	
	
	

	/*
	 * -----------------------------------------------------------------------------------------------
	 * ------------------------------------------数据字典字段 D----------------------------------------
	 * -----------------------------------------------------------------------------------------------
	 */
	
	/** 疾病诊断代码类型 */
	public static String TYPE_OF_DISEASE_DIAGNOSIS_CODE = "D#1&WS/TXXX-2009CV5502.20";
	/** 疾病诊断代码类型名称 */
	public static String TYPE_OF_DISEASE_DIAGNOSIS_NAME = "D#2&WS/TXXX-2009CV5502.20";
	/** 疾病诊断类别代码 */
	public static String DISEASE_DIAGNOSIS_CAT_CODE = "D#1&WS/TXXX-2009CV5502.20";
	/** 疾病诊断类别名称 */
	public static String DISEASE_DIAGNOSIS_CAT_NAME = "D#2&WS/TXXX-2009CV5502.20";
	/** 医疗付费方式代码 */
	public static String CHARGE_TYPE_CODE = "D#1&TJ-YLFFFS-00";
	/** 医疗付费方式名称 */
	public static String CHARGE_TYPE_NAME = "D#2&TJ-YLFFFS-00";
	/** 住院患者尸检标志代码 */
	public static String AUTOPSY_FLAG_CODE = "D#1&TJ-SF-00";
	/** 住院患者尸检标志 */
	public static String AUTOPSY_FLAG_NAME = "D#2&TJ-SF-00";
	/** 住院病例病案质量代码 */
	public static String HOSPITIZAL_RECORD_QUALITY_CODE = "D#1&TJ-ZYBLBAZL-00";
	/** 住院病例病案质量 */
	public static String HOSPITIZATIONS_RECORD_QUALITY = "D#2&TJ-ZYBLBAZL-00";
	/** 离院方式代码 */
	public static String DISCHARGE_WAY_CODE = "D#1&TJ-LYFS-00";
	/** 离院方式 */
	public static String DISCHARGE_WAY_NAME = "D#2&TJ-LYFS-00";
	/** 使用中医诊疗设备代码 */
	public static String CHINESE_MEDICAL_DEVICE_CODE = "D#1&TJ-SF-00";
	/** 使用中医诊疗设备 */
	public static String CHINESE_MEDICAL_DEVICE_NAME = "D#2&TJ-SF-00";
	/** 使用中医诊疗技术代码 */
	public static String CHINESE_MEDICAL_TECHNIQUE_CODE = "D#1&TJ-SF-00";
	/** 使用中医诊疗技术 */
	public static String CHINESE_MEDICAL_TECHNIQUE_NAME = "D#2&TJ-SF-00";
	/** 辨证施护代码 */
	public static String SYNDROME_NURSING_CODE = "D#1&TJ-SF-00";
	/** 辨证施护 */
	public static String SYNDROME_NURSING_NAME = "D#2&TJ-SF-00";
	/** 出院中医主病-入院病情代码 */
	public static String TCM_HD_ADMISSION_COND_CODE = "D#1&TJ-RYBQ-00";
	/** 出院中医主病-入院病情 */
	public static String TCM_HD_ADMISSION_COND_NAME = "D#2&TJ-RYBQ-00";
	/** 出院西医主要诊断-入院病情代码 */
	public static String WMD_ADMISSION_CONDITION_CODE = "D#1&TJ-RYBQ-00";
	/** 出院西医主要诊断-入院病情 */
	public static String WMD_ADMISSION_CONDITION_NAME = "D#2&TJ-RYBQ-00";
	
	/** 检查-类别代码 */
	public static String EXAMINATION_TYPE_CODE = "D#1&WS/TXXX-2009CV5199.01";
	/** 检查-类别名称 */
	public static String EXAMINATION_TYPE_NAME = "D#2&WS/TXXX-2009CV5199.01";
	/** 检查部位 */
	public static String CHECK_PARKS = "D#2&WS/TXXX-2009CV5201.23";

	/** 医疗付款方式代码 */
	public static String EXPENSES_PAYMENT_CODE = "D#1&WS/TXXX-2009CV5600.04";
	/** 医疗付款方式名称 */
	public static String EXPENSES_PAYMENT_NAME = "D#2&WS/TXXX-2009CV5600.04";
	/** 挂号类别代码 */
	public static String REGISTER_TYPE_CODE = "D#1&TJ-RYTJ-00";
	/** 挂号类别名称 */
	public static String REGISTER_TYPE_NAME = "D#2&TJ-RYTJ-00";

	/** 中药类别代码 */
	public static String MEDICINE_CATEGORY_CODE = "D#1&WS/TXXX-2009CV5301.05";
	/** 中药类别名称 */
	public static String MEDICINE_CATEGORY_NAME = "D#2&WS/TXXX-2009CV5301.05";
	
	/** 联系人关系 */
	public static String CONTACT_RELATIONSHIP_CODE = "D#1&TJ-LXRGX-00";
	/** 联系人关系名称 */
	public static String CONTACT_RELATIONSHIP_NAME = "D#2&TJ-LXRGX-00";


	/** 出院中医主证-入院病情代码 */
	public static String TCM_MS_ADMISSION_COND_CODE = "D#1&TJ-RYBQ-00";
	/** 出院中医主证-入院病情 */
	public static String TCM_MS_ADMISSION_COND_NAME = "D#2&TJ-RYBQ-00";
	
	/** 入院途径代码 */
	public static String ADMISSION_PATHWAY_CODE = "D#1&TJ-RYTJ-00";
	/** 入院途径 */
	public static String ADMISSION_PATHWAY_NAME = "D#2&TJ-RYTJ-00";
	/** 治疗类别代码 */
	public static String THERAPEUTIC_CLASS_CODE = "D#1&TJ-ZLLB-00";
	/** 治疗类别名称 */
	public static String THERAPEUTIC_CLASS_NAME = "D#2&TJ-ZLLB-00";
	/** 使用医疗机构中药制剂代码 */
	public static String CHINESE_MEDICINE_CODE = "D#1&TJ-SF-00";
	/** 使用医疗机构中药制剂 */
	public static String CHINESE_MEDICINE = "D#2&TJ-SF-00";
	/** 药物类型代码 */
	public static String DRUG_TYPE_CODE = "D#1&WS/TXXX-2009CV5301.06";
	/** 药物类型名称 */
	public static String DRUG_TYPE_NAME = "D#1&WS/TXXX-2009CV5301.06";
	/** 药物使用-途径代码 */
	public static String DRUG_USE_MEANS_CODE = "D#1&WS/TXXX-2009CV5201.22";
	/** 药物使用-途径名称 */
	public static String DRUG_USE_MEANS_NAME = "D#2&WS/TXXX-2009CV5201.22";
	/** 药物剂型代码 */
	public static String DOSAGE_FORM_CODE = "D#1&WS/TXXX-2009CV5301.01";
	/** 药物剂型名称 */
	public static String DOSAGE_FORM_NAME = "D#2&WS/TXXX-2009CV5301.01";
	/** 门诊 患者医疗付款方式代码 */
	public static String PAYMENT_METHOD_CODE = "D#1&WS/TXXX-2009CV5600.02";
	/** 门诊 患者医疗付款方式	 */
	public static String PAYMENT_METHOD_NAME = "D#2&WS/TXXX-2009CV5600.02";
	/** 门诊 收据项目分类代码*/
	public static String CLASS_ON_RCPT_CODE = "D#1&WS/TXXX-2009CV5600.01";
	/** 门诊 收据项目分类名称*/
	public static String CLASS_ON_RCPT_NAME = "D#2&WS/TXXX-2009CV5600.01";
	/** 收费等级 */
	public static String PAY_LEVEL = "E#2&1=无自付,2=有自付,3=全自付";
	
//	/** 住院 患者医疗付款方式代码 */
//public static String PAYMENT_METHOD_CODE = "D#1&WS/TXXX-2009CV5600.04";
//	/** 住院 患者医疗付款方式	 */
//	public static String PAYMENT_METHOD_NAME = "D#2&WS/TXXX-2009CV5600.04";
//	/** 住院 收据项目分类代码*/
//	public static String CLASS_ON_RCPT_CODE = "D#1&WS/TXXX-2009CV5600.03";
//	/** 住院 收据项目分类名称*/
//	public static String CLASS_ON_RCPT_NAME = "D#2&WS/TXXX-2009CV5600.03";
	
	
	/** 出院方式代码 */
	public static String DISCHARGE_DISPOSITION_CODE = "D#1&WS/TXXX-2009CV5501.16";
	/** 出院方式名称 */
	public static String DISCHARGE_DISPOSITION_NAME = "D#2&WS/TXXX-2009CV5501.16";
	
	// 手术信息
	/** 切口等级代码 */
	public static String INCISION_GRADE_CODE = "D#1&TJ-QKDJ-00";
	/** 切口等级名称 */
	public static String INCISION_GRADE_NAME = "D#2&TJ-QKDJ-00";
	/** 切口愈合代码*/
	public static String INCISION_HEALING_CODE = "D#1&TJ-QKYH-00";
	/** 切口愈合名称 */
	public static String INCISION_HEALING_NAME = "D#2&TJ-QKYH-00";
	/** 操作部位代码 */
	public static String OPERATION_BODY_PARKS = "D#2&WS/TXXX-2009CV5201.23";

	
	
//	public static String AAA = "F#2&COMM_CONFIG_SEX";
}
