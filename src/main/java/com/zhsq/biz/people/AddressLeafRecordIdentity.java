package com.zhsq.biz.people;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.abc.fuse.identity.BaseLeafRecordIdentity;
import com.abc.rrc.record.LeafRecord;
import com.abc.rrc.record.Record;
import com.zhsq.biz.constant.people.PeopleItem;

@Repository("XFJD059")
public class AddressLeafRecordIdentity extends BaseLeafRecordIdentity{

	@Override
	public LeafRecord bizzIdentify(LeafRecord paramLeafRecord, Record paramRecord) {
		 Object sqlValue = paramLeafRecord.findAttribute(PeopleItem.居住信息_居住地门牌号).getSqlValue();
		
		 Collection<LeafRecord> findLeafs = paramRecord.findLeafs(PeopleItem.居住信息);
		 for (LeafRecord leafRecord : findLeafs) {
			 Object sqlValue2 = leafRecord.findAttribute(PeopleItem.居住信息_居住地门牌号).getSqlValue();
			 
			 if (sqlValue2.equals(sqlValue)) {
				 return paramLeafRecord;
			 }
		}
		
		return null;
	}
}