package com.songzhi.generate;

import static com.songzhi.utils.DictionaryManager.getDictionary;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.songzhi.model.TableModel;
import com.songzhi.utils.Dictionary;

/**
 * 字典值生成器
 */
public class DictionaryFromFileGenerator extends DataGeneratorAdapter {
	private static Logger log = Logger.getLogger(DictionaryFromFileGenerator.class);
	
	// 参数设置
	private int dictType = 0;	//字段类型  0：键与值 ， 1：键, 2：值  默认为0
	private boolean random = true;	//是否随机生成 
	private String dictName;	//字典名称
	private String relationColumn;	//关联字段	该字段需要在模型文件中自己定义
	private String relationValue;	//关联值	该字段为关联字段的值，关联字段的值需先生成
	
	@Override
	public Object generator(TableModel tableModel) {
		Object data = null;
		try {			
			Dictionary dict = getDictionary(dictName);
			
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
			log.error(e.getMessage());
		}
		return data;
	}

	/**字段类型  0：键与值 ， 1：键, 2：值  默认为0*/
	public void setDictType(int dictType) {
		this.dictType = dictType;
	}	
	
	public void setRandom(boolean random) {
		this.random = random;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	@Override
	public void initialize(Container container) {
		// TODO Auto-generated method stub
		
	}

	public String getRelationColumn() {
		return relationColumn;
	}

	public void setRelationColumn(String relationColumn) {
		this.relationColumn = relationColumn;
	}

	public String getRelationValue() {
		return relationValue;
	}

	public void setRelationValue(String relationValue) {
		this.relationValue = relationValue;
	}
}
