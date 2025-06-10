<template>
  <div class="page_search">
	<div class="warp">
	  <div class="container">
		<div class="row">
		  <div class="col-12">
			<div class="card_result_search">
			  <div class="title">搜索结果</div>
				<!-- 商品搜索结果 -->
			  <list_result_search
				:list="result_goods"
				title="汽车配件"
				source_table="goods"
			  ></list_result_search>
				<!-- 文章搜索结果 -->
			  <list_result_search
				:list="result_article"
				title="配件资讯"
				source_table="article"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/commodity_management/list', 'get')"
				:list="result_commodity_management_commodity_number"
				title="商品管理商品编号"
				source_table="commodity_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/commodity_management/list', 'get')"
				:list="result_commodity_management_commodity_type"
				title="商品管理商品类型"
				source_table="commodity_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/commodity_management/list', 'get')"
				:list="result_commodity_management_trade_name"
				title="商品管理商品名称"
				source_table="commodity_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/purchase_registration/list', 'get')"
				:list="result_purchase_registration_commodity_number"
				title="进货登记商品编号"
				source_table="purchase_registration"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/purchase_registration/list', 'get')"
				:list="result_purchase_registration_commodity_type"
				title="进货登记商品类型"
				source_table="purchase_registration"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/purchase_registration/list', 'get')"
				:list="result_purchase_registration_trade_name"
				title="进货登记商品名称"
				source_table="purchase_registration"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/supplier_management/list', 'get')"
				:list="result_supplier_management_business_name"
				title="进货商管理商家名称"
				source_table="supplier_management"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/sales_registration/list', 'get')"
				:list="result_sales_registration_commodity_number"
				title="销售登记商品编号"
				source_table="sales_registration"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/sales_registration/list', 'get')"
				:list="result_sales_registration_commodity_type"
				title="销售登记商品类型"
				source_table="sales_registration"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/sales_registration/list', 'get')"
				:list="result_sales_registration_trade_name"
				title="销售登记商品名称"
				source_table="sales_registration"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/financial_statistics/list', 'get')"
				:list="result_financial_statistics_date"
				title="财务统计日期"
				source_table="financial_statistics"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/sales_system/list', 'get')"
				:list="result_sales_system_remarks"
				title="销售系统备注"
				source_table="sales_system"
			  ></list_result_search>
			</div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</template>

<script>
import mixin from "../../mixins/page.js";
import list_result_search from "../../components/diy/list_result_search.vue";

export default {
  mixins: [mixin],
  data() {
	return {
	  "query": {
		word: "",
	  },
	  "result_goods": [],
	  "result_article": [],
			"result_commodity_management_commodity_number":[],
			"result_commodity_management_commodity_type":[],
			"result_commodity_management_trade_name":[],
			"result_purchase_registration_commodity_number":[],
			"result_purchase_registration_commodity_type":[],
			"result_purchase_registration_trade_name":[],
			"result_supplier_management_business_name":[],
			"result_sales_registration_commodity_number":[],
			"result_sales_registration_commodity_type":[],
			"result_sales_registration_trade_name":[],
			"result_financial_statistics_date":[],
			"result_sales_system_remarks":[],
	};
  },
  methods: {
	/**
	 * 获取汽车配件
	 */
	get_goods() {
	  this.$get("~/api/goods/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_goods = json.result.list;
		}
	  });
	},
	/**
	 * 获取文章
	 */
	get_article() {
	  this.$get("~/api/article/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_article = json.result.list;
		}
	  });
	},
	/**
	 * 获取commodity_number
	 */
	get_commodity_management_commodity_number(){
		this.$get("~/api/commodity_management/get_list?like=0", { page: 1, size: 10, "commodity_number": this.query.word }, (json) => {
		  if (json.result) {
			var result_commodity_management_commodity_number = json.result.list;
			result_commodity_management_commodity_number.map(o => o.title = o['commodity_number'])
	  			this.result_commodity_management_commodity_number = result_commodity_management_commodity_number
		 	}
		});
	},
	/**
	 * 获取commodity_type
	 */
	get_commodity_management_commodity_type(){
		this.$get("~/api/commodity_management/get_list?like=0", { page: 1, size: 10, "commodity_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_commodity_management_commodity_type = json.result.list;
			result_commodity_management_commodity_type.map(o => o.title = o['commodity_type'])
	  			this.result_commodity_management_commodity_type = result_commodity_management_commodity_type
		 	}
		});
	},
	/**
	 * 获取trade_name
	 */
	get_commodity_management_trade_name(){
		this.$get("~/api/commodity_management/get_list?like=0", { page: 1, size: 10, "trade_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_commodity_management_trade_name = json.result.list;
			result_commodity_management_trade_name.map(o => o.title = o['trade_name'])
	  			this.result_commodity_management_trade_name = result_commodity_management_trade_name
		 	}
		});
	},
	/**
	 * 获取commodity_number
	 */
	get_purchase_registration_commodity_number(){
		this.$get("~/api/purchase_registration/get_list?like=0", { page: 1, size: 10, "commodity_number": this.query.word }, (json) => {
		  if (json.result) {
			var result_purchase_registration_commodity_number = json.result.list;
			result_purchase_registration_commodity_number.map(o => o.title = o['commodity_number'])
	  			this.result_purchase_registration_commodity_number = result_purchase_registration_commodity_number
		 	}
		});
	},
	/**
	 * 获取commodity_type
	 */
	get_purchase_registration_commodity_type(){
		this.$get("~/api/purchase_registration/get_list?like=0", { page: 1, size: 10, "commodity_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_purchase_registration_commodity_type = json.result.list;
			result_purchase_registration_commodity_type.map(o => o.title = o['commodity_type'])
	  			this.result_purchase_registration_commodity_type = result_purchase_registration_commodity_type
		 	}
		});
	},
	/**
	 * 获取trade_name
	 */
	get_purchase_registration_trade_name(){
		this.$get("~/api/purchase_registration/get_list?like=0", { page: 1, size: 10, "trade_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_purchase_registration_trade_name = json.result.list;
			result_purchase_registration_trade_name.map(o => o.title = o['trade_name'])
	  			this.result_purchase_registration_trade_name = result_purchase_registration_trade_name
		 	}
		});
	},
	/**
	 * 获取business_name
	 */
	get_supplier_management_business_name(){
		this.$get("~/api/supplier_management/get_list?like=0", { page: 1, size: 10, "business_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_supplier_management_business_name = json.result.list;
			result_supplier_management_business_name.map(o => o.title = o['business_name'])
	  			this.result_supplier_management_business_name = result_supplier_management_business_name
		 	}
		});
	},
	/**
	 * 获取commodity_number
	 */
	get_sales_registration_commodity_number(){
		this.$get("~/api/sales_registration/get_list?like=0", { page: 1, size: 10, "commodity_number": this.query.word }, (json) => {
		  if (json.result) {
			var result_sales_registration_commodity_number = json.result.list;
			result_sales_registration_commodity_number.map(o => o.title = o['commodity_number'])
	  			this.result_sales_registration_commodity_number = result_sales_registration_commodity_number
		 	}
		});
	},
	/**
	 * 获取commodity_type
	 */
	get_sales_registration_commodity_type(){
		this.$get("~/api/sales_registration/get_list?like=0", { page: 1, size: 10, "commodity_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_sales_registration_commodity_type = json.result.list;
			result_sales_registration_commodity_type.map(o => o.title = o['commodity_type'])
	  			this.result_sales_registration_commodity_type = result_sales_registration_commodity_type
		 	}
		});
	},
	/**
	 * 获取trade_name
	 */
	get_sales_registration_trade_name(){
		this.$get("~/api/sales_registration/get_list?like=0", { page: 1, size: 10, "trade_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_sales_registration_trade_name = json.result.list;
			result_sales_registration_trade_name.map(o => o.title = o['trade_name'])
	  			this.result_sales_registration_trade_name = result_sales_registration_trade_name
		 	}
		});
	},
	/**
	 * 获取date
	 */
	get_financial_statistics_date(){
		this.$get("~/api/financial_statistics/get_list?like=0", { page: 1, size: 10, "date": this.query.word }, (json) => {
		  if (json.result) {
			var result_financial_statistics_date = json.result.list;
			result_financial_statistics_date.map(o => o.title = o['date'])
	  			this.result_financial_statistics_date = result_financial_statistics_date
		 	}
		});
	},
	/**
	 * 获取remarks
	 */
	get_sales_system_remarks(){
		this.$get("~/api/sales_system/get_list?like=0", { page: 1, size: 10, "remarks": this.query.word }, (json) => {
		  if (json.result) {
			var result_sales_system_remarks = json.result.list;
			result_sales_system_remarks.map(o => o.title = o['remarks'])
	  			this.result_sales_system_remarks = result_sales_system_remarks
		 	}
		});
	},

  },
  components: { list_result_search },
	created(){
    this.query.word = this.$route.query.word || "";
  },
  mounted() {
	this.get_goods();
	this.get_article();
		this.get_commodity_management_commodity_number();
		this.get_commodity_management_commodity_type();
		this.get_commodity_management_trade_name();
		this.get_purchase_registration_commodity_number();
		this.get_purchase_registration_commodity_type();
		this.get_purchase_registration_trade_name();
		this.get_supplier_management_business_name();
		this.get_sales_registration_commodity_number();
		this.get_sales_registration_commodity_type();
		this.get_sales_registration_trade_name();
		this.get_financial_statistics_date();
		this.get_sales_system_remarks();
  },
  watch: {
	$route() {
	  $.push(this.query, this.$route.query);
	  this.get_goods();
	  this.get_article();
	  this.get_commodity_management_commodity_number();
	  this.get_commodity_management_commodity_type();
	  this.get_commodity_management_trade_name();
	  this.get_purchase_registration_commodity_number();
	  this.get_purchase_registration_commodity_type();
	  this.get_purchase_registration_trade_name();
	  this.get_supplier_management_business_name();
	  this.get_sales_registration_commodity_number();
	  this.get_sales_registration_commodity_type();
	  this.get_sales_registration_trade_name();
	  this.get_financial_statistics_date();
	  this.get_sales_system_remarks();
	},
  },
};
</script>

<style scoped>
.card_search {
  text-align: center;
}
.card_result_search>.title {
  text-align: center;
  padding: 10px 0;
}
</style>
