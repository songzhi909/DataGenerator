package com.songzhi.generate;

import static com.songzhi.utils.DictionaryManager.getDictionary;
import static com.songzhi.utils.DictionaryManager.getDictionaryFromClassCode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;
import com.songzhi.utils.Dictionary;

/**
 * 文件字典生成器
 */
public class DictionaryFromDBGenerator extends DataGeneratorAdapter{ 
	private static Logger log = Logger.getLogger(DictionaryFromDBGenerator.class);
	
	// 参数设置
	private int dictType = 0;	//字段类型  0：键与值 ， 1：键, 2：值  默认为0
	private boolean random = true;	//是否随机生成 
	private String dictName;	//表名
	private String dictCode;	//代码字段名
	private String dictValue;	//值字段名
	private String condition;	//条件
	private String relationColumn;	//关联字段	该字段需要在模型文件中自己定义
	private String relationValue;	//关联值	该字段为关联字段的值，关联字段的值需先生成
	private String classCode;	//字典类别
	
	@Override
	public Object generator(TableModel tableModel) {
		Object data = null;
		try {			
			Dictionary dict = null;
			if(StringUtils.isNotBlank(classCode)) {
				dict = getDictionaryFromClassCode(classCode);
			}else {
				if(StringUtils.isBlank(dictName) || StringUtils.isBlank(dictCode) || StringUtils.isBlank(dictValue)) {
					throw new RuntimeException("缺失参数： ");
				}
				dict = getDictionary(dictName, dictCode, dictValue, condition);
				
			}
			
			
			if(dictType == 0) {	//键值对
				if(random) data = dict.randomEntry();
				else	data = dict.nextUniqueEntry();
				return data;
			}
			
			if(StringUtils.isBlank(relationColumn)) {	//无关联字段
				 if(dictType == 1) {	//键
					if(random) data = dict.randomKey();
					else	data = dict.nextUniqueKey();
				}else if(dictType == 2) {	//值
					if(random) data = dict.randomValue();
					else	data = dict.nextUniqueValue();
				}else {
					log.error("字典类型不内容不匹配：" + dictType);
				}
			}else { //有关联字段
				if(StringUtils.isNotBlank(relationValue)) {
					if(dictType == 1) { //键
						data = dict.getKeyByValue(relationValue);
					}else if(dictType == 2) {//值
						data = dict.getValueByKey(relationValue);
					}else {
						log.error("字典类型不内容不匹配：" + dictType);
					}
				}else {
					log.error("关联字段【" + relationColumn + "】的值不能为空!");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return data;
	}
	
	public void setRandom(boolean random) {
		this.random = random;
	}

	public void setTableName(String tableName) {
		this.dictName = tableName;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public static void main(String[] args) {
		DictionaryFromDBGenerator generator = new DictionaryFromDBGenerator();
		generator.setTableName("COMM_DICT_PUBLIC_CHAR");
		generator.setDictType(2);
		generator.setRandom(true); 
		generator.setDictCode("DICT_CODE");
		generator.setDictValue("DICT_VALUE");
		generator.setCondition("CLASS_CODE='TJ-YLFFFS-00'");
		System.out.println(generator.generator(null));
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	
	public int getDictType() {
		return dictType;
	}

	public void setDictType(int dictType) {
		this.dictType = dictType;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getRelationColumn() {
		return relationColumn;
	}

	public String getRelationValue() {
		return relationValue;
	}

	public void setRelationColumn(String relationColumn) {
		this.relationColumn = relationColumn;
	}

	public void setRelationValue(String relationValue) {
		this.relationValue = relationValue;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
}
