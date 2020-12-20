package BulkImport;

/**
 * HBase table columns for the 'md' column family
 */
public enum HColEnum {
	//MD_COL_A("ID".getBytes()), 
	//MD_COL_B("DatabaseID".getBytes()),
	MD_COL_C("ExternalID".getBytes()), 
	MD_COL_D("ParentDocumentExternalID".getBytes()),
	MD_COL_E("DocumentTypeDescription".getBytes()), 
	MD_COL_F("ProjectID".getBytes()),
	MD_COL_G("Title".getBytes());

	// These are the exact names from the HBase Physical Model
        MD_CQ_DOCID("ctntElmtExtnlId".getBytes()),
        MD_CQ_DOCNAME("ctntElmtDescTxt".getBytes()),
        MD_CQ_DBID("ctntInvExtnlId".getBytes()),
        MD_CQ_DBNAME("ctntInvDescTxt".getBytes()),
        MD_CQ_CTSTMP("creatTstmp".getBytes()),
        MD_CQ_LMUID("lastModAssocId".getBytes()),
        MD_CQ_PARSW("parntSw".getBytes()),
        MD_CQ_WEFFID("workEfrtExtnlId".getBytes()),
        MD_CQ_WEFFNUM("workEfrtNm".getBytes()),
        MD_CQ_PARDOCID("parntElmtExtnlId".getBytes()),
        MD_CQ_CFDTLINFOSW("cfdtlInfoSw".getBytes()),
        MD_CQ_LSTSCNTSTMP("lastScanTstmp".getBytes()),
        MD_CQ_DOCTYPE("ctntElmtTypeDescTxt".getBytes()),
        MD_CQ_DBTYPE("ctntInvTypeDescTxt".getBytes()),
        MD_CQ_IRPCODE("infoRetnProgCd".getBytes()),
        MD_CQ_IRPRETCAT("infoRetnProgRetnCtgryTxt".getBytes()),
        MD_CQ_IRPVAL("infoRetnProgValueTxt".getBytes()),
        MD_CQ_IRPTRIGDATE("infoRetnProgTrgrDt".getBytes()),
        MD_CQ_IRPLOCCODE("infoRetnProgLocCd".getBytes()),
        MD_CQ_IRPLOCNUM("infoRetnProgLocNm".getBytes()),
        MD_CQ_IRPDELDATE("infoRetnProgDlteDt".getBytes());

	private final byte[] columnName;

	HColEnum(byte[] column) {
		this.columnName = column;
	}

	public byte[] getColumnName() {
		return this.columnName;
	}
}



("creatTstmp".getBytes()),
("lastModAssocId".getBytes()),
("ctntElmtDescTxt".getBytes()),
("ctntElmtExtnlId".getBytes()),
("ctntInvDescTxt".getBytes()),
("parntSw".getBytes()),
("workEfrtExtnlId".getBytes()),
("workEfrtNm".getBytes()),
("parntElmtExtnlId".getBytes()),
("ctntInvExtnlId".getBytes()),
("cfdtlInfoSw".getBytes()),
("lastScanTstmp".getBytes()),
("ctntElmtTypeDescTxt".getBytes()),
("ctntInvTypeDescTxt".getBytes()),
("infoRetnProgCd".getBytes()),
("infoRetnProgRetnCtgryTxt".getBytes()),
("infoRetnProgValueTxt".getBytes()),
("infoRetnProgTrgrDt".getBytes()),
("infoRetnProgLocCd".getBytes()),
("infoRetnProgLocNm".getBytes()),
("infoRetnProgDlteDt".getBytes()),

