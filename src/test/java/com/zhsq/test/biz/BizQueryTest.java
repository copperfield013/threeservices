package com.zhsq.test.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.complexus.RecordComplexus;
import com.abc.fuse.fg.FGFusionContext;
import com.abc.fuse.fg.FunctionalGroup;
import com.abc.fuse.fg.ImproveResult;
import com.abc.fuse.fg.OneRoundImprovement;
import com.abc.fuse.improve.attribute.FuseLeafAttrFactory;
import com.abc.fuse.improve.attribute.leaf.FuseLeafAttribute;
import com.abc.fuse.improve.ops.builder.RootRecordBizzOpsBuilder;
import com.abc.hc.FusionContext;
import com.abc.hc.HCFusionContext;
import com.abc.ops.builder.RecordRelationOpsBuilder;
import com.abc.ops.complexus.OpsComplexus;
import com.abc.panel.Integration;
import com.abc.panel.IntegrationMsg;
import com.abc.panel.PanelFactory;
import com.abc.record.query.RecordQueryPanel;
import com.abc.rrc.factory.DBAttributeFactory;
import com.abc.rrc.query.queryrecord.criteria.Criteria;
import com.abc.rrc.record.Attribute;
import com.abc.rrc.record.RootRecord;

@ContextConfiguration(locations = "classpath*:spring-core.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BizQueryTest {
	private static Logger logger = Logger.getLogger(BizQueryTest.class);

	@Test
	public void select() {
		Collection<String> codes = RecordQueryPanel.query(buildCriteria());
		Integration integration = PanelFactory.getIntegration();
		HCFusionContext context = new HCFusionContext();
		context.putFunctionalGroup("ABCE010",  new PeopleBNB());
		context.setSource(FusionContext.SOURCE_COMMON);
		if (codes != null) {
			logger.debug("总数：" + codes.size());
			int i = 0;
			for (String code : codes) {
				if (i++ < 2) {
					IntegrationMsg msg = integration.integrate(context, code);
					RootRecord rootRecord = RecordQueryPanel.queryRoot("ABCE010", msg.getCode());
					logger.debug(rootRecord.toJson());
				}
			}
		}
	}

	private List<Criteria> buildCriteria() {

		List<Criteria> criterias = new ArrayList<Criteria>();
		/*Criteria common;
		common = CriteriaFactory.createCriteria("ABCE010", "SW0208", "刘志华", CriteriaSymbol.EQUAL);// 姓名
		criterias.add(common);*/
		return criterias;
	}

	
	protected class PeopleBNB implements FunctionalGroup, OneRoundImprovement {


		@Override
		public ImproveResult improve(FGFusionContext context, String recordCode, RecordComplexus recordComplexus) {

			ImproveResult imprveResult = new ImproveResult();

			String recordType = recordComplexus.getRootRecord(recordCode).getName();

			RootRecordBizzOpsBuilder rootRecordOpsBuilder = RootRecordBizzOpsBuilder.getInstance(recordType, recordCode);
			Collection<FuseLeafAttribute> fuseAttrs = new ArrayList<FuseLeafAttribute>();
			/*fuseAttrs.add(FuseLeafAttrFactory.newInstance(recordCode, "SW2070", "a7sdfg54319c4da0bf47fc72488231xx",
					DBAttributeFactory.newInstance("SW2074", "改动一下")));*/

			rootRecordOpsBuilder.addLeafAttribute(fuseAttrs);
			Collection<Attribute> attrs = new ArrayList<Attribute>();
			/*attrs.add(DBAttributeFactory.newInstance("SW0208", "刘志华X"));*/
			rootRecordOpsBuilder.addAttribute(attrs);
			imprveResult.setRootRecordOps(rootRecordOpsBuilder.getRootRecordOps());

			RecordRelationOpsBuilder recordRelationOpsBuilder = RecordRelationOpsBuilder.getInstance(recordCode,
					recordType);
			recordRelationOpsBuilder.putRelation("R09110032", "a7eede54319c4da0bf47fc72488231da");
			imprveResult.setRecordRelationOps(recordRelationOpsBuilder.getRecordRelationOps());

			return imprveResult;
		}

		@Override
		public ImproveResult preImprove(FGFusionContext var1, String var2, OpsComplexus var3, RecordComplexus var4) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ImproveResult postImprove(FGFusionContext var1, String var2, RecordComplexus var3) {
			// TODO Auto-generated method stub
			return null;
		}


	}

}
