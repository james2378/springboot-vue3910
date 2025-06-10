<template>
	<div class="page_root" id="root_index">
		<div class="warp">
			<div class="container-fluid">
				<el-row>
<!--					<el-col :span="4">-->
<!--						<mm_label bg_color="bg_blue" icon="el-icon-s-order" :url="url_order_count" unit="笔"-->
<!--								  title="待处理订单"></mm_label>-->
<!--					</el-col>-->
<!--					<el-col :span="4">-->
<!--						<mm_label bg_color="bg_yellow" icon="el-icon-sold-out" :url="url_goods_count" unit="件"-->
<!--								  title="出售商品"></mm_label>-->
<!--					</el-col>-->
<!--					<el-col :span="4">-->
<!--						<mm_label bg_color="bg_cyan" icon="el-icon-money" :url="url_order_day_price" unit="元"-->
<!--								  title="日营业额"></mm_label>-->
<!--					</el-col>-->
					<el-col :span="4">
						<mm_label bg_color="bg_purple" icon="el-icon-user-solid" :url="url_user_count" unit="人"
								  title="用户数量"></mm_label>
					</el-col>
				</el-row>

				<el-row>
					<!--<el-col :span="8">
						<div class="card chart">
							<pieChart v-if="list_goods_count.length" :list="list_goods_count" :title="'商品分类统计图'"
									  :type="'数量(占比)'"></pieChart>
							<div v-if="!list_goods_count.length">订单没有符合条件的数据</div>
						</div>
					</el-col>
					<el-col :span="8">
						<div class="card chart" >
							<barChart v-if="list_goods_sum.length" :list="list_goods_sum" :title="'商品年销量统计图'">
							</barChart>
							<div v-if="!list_goods_sum.length">订单没有符合条件的数据</div>
						</div>
					</el-col>
					<el-col :span="8">
						<div class="card chart" >
							<lineChart v-if="list_goods_price.length" :list="list_goods_price" :title="'商品销售额统计图'">
							</lineChart>
							<div v-if="!list_goods_price.length">订单没有符合条件的数据</div>
						</div>
					</el-col>-->
					<el-col v-if="user_group == '管理员' || $check_figure('/financial_statistics/table')" :span="8">
						<div class="card chart">
							<newBarChart v-if="bar_obj_financial_statistics.values.length > 0" id="bar_obj_financial_statistics" :vm="bar_obj_financial_statistics" :title="'财务统计统计'">
							</newBarChart>
							<div v-if="!bar_obj_financial_statistics.values.length">财务统计没有符合条件的数据</div>
						</div>
					</el-col>
				</el-row>
			</div>
		</div>
	</div>
</template>
<script>
	import mixin from "@/mixins/page.js";
	import pieChart from "@/components/charts/pie_chart";
	import barChart from "@/components/charts/bar_chart";
	import newBarChart from "@/components/charts/new_bar_chart";
	import lineChart from "@/components/charts/line_chart";
	import newLineChart from "@/components/charts/new_line_chart";
	import mm_label from "@/components/mm_label.vue";
	export default {
		mixins: [mixin],
		name: "Home",
		components: {
			pieChart,
			barChart,
			newBarChart,
			lineChart,
			newLineChart,
			mm_label
		},
		data() {
			return {
				isAdmin: false,
				recognitionType: "",
				activeName: "third",
				/*list_goods_count: [],
				list_goods_sum: [],
				list_goods_price: [],*/
				bar_obj_financial_statistics: {
					names:[],
					xAxis: [],
					values:[]
				},
				url_order_count: "~/api/order/count?state=2",
				url_order_day_price: this.url_orderDayPrice(),
				url_goods_count: "~/api/goods/count?",
				url_user_count: "~/api/user/count?",
			};
		},
		created() {
			this.getUserInfo();
			// 执行财务统计数据获取
			this.get_list_financial_statistics();
			/*this.get_goods_count();
			this.get_goods_sum_group();
			this.get_goods_sum_price();*/
			
			
		},
		mounted() {},
		methods: {
			async get_nickname(list,flag){
				if (flag) {
					for (let i=0;i<list.length;i++){
						await this.$get(
								"~/api/user/get_obj?user_id="+list[i],
								null,
								(json) => {
									if (json.result) {
										list[i] = json.result.obj.nickname;
									}
								});
					}
				}else {
					for (let i=0;i<list.length;i++){
						await this.$get(
								"~/api/user/get_obj?user_id="+list[i].name,
								null,
								(json) => {
									if (json.result) {
										list[i].name = json.result.obj.nickname;
									}
								});
					}
				}
			},
			// 获取财务统计统计柱状图
			async get_list_financial_statistics() {
				let name_list = [];
				let query_str = "";
				name_list.push("营业额");
				query_str = query_str+"turnover"+","
				let group_by_value = "date";
				let flag = false;
				let date_flag = "日期"
				this.bar_obj_financial_statistics.names = name_list
				query_str = query_str.substr(0,query_str.length-1);
				let data = {};
				await this.$get(
						"~/api/financial_statistics/bar_group?field="+query_str+"&groupby="+group_by_value,
						data,
						(json) => {
							if (json.result) {
								let xAxis = [];
								let values = [];
								json.result.list.map((o) => {
									if (date_flag === "日期") {
										xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
									}else if (date_flag === "时间") {
										xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
									}else {
										xAxis.push(o[0]);
									}
									values.push(o.splice(1))
								});
								this.bar_obj_financial_statistics.xAxis = xAxis;
								this.bar_obj_financial_statistics.values = values;
							}
							if (flag){
								this.get_nickname(this.bar_obj_financial_statistics.xAxis,true);
							}
						});
			},
			// 获取商品数量分类统计图
			/*get_goods_count() {
				this.$get("~/api/goods/count_group?groupby=type", null, (json) => {
					if (json.result) {
						var list = json.result.list;
						this.list_goods_count = list.map((o) => {
							return {
								name: o.type,
								value: o.count,
							};
						});
					}
				});
			},

			// 获取销量七天报
			get_goods_sum_group() {
				for (var i = 6; i >= 0; i--) {
					this.get_goods_sum_group_sub(this.list_goods_sum, i);
				}
			},

			// 获取X天销量
			async get_goods_sum_group_sub(list, day) {
				var now = new Date();
				var time = now.addDays(-day).toStr("yyyy-MM-dd");
				await this.$get(
						`~/api/order/sum_group?field=price&groupby=type&create_time_min= 00:00:00&create_time_max= 23:59:59`,
						null,
						(json) => {
							if (json.result) {
								json.result.list.map((o) => {
									list.push({
										time,
										value: o.sum,
										name: o.type,
									})
								});
							}
						});
			},

			// 获取销售七天报
			get_goods_sum_price() {
				for (var i = 6; i >= 0; i--) {
					this.get_goods_sum_price_sub(this.list_goods_price, i);
				}
			},

			// 获取X天销售
			async get_goods_sum_price_sub(list, day) {
				var now = new Date();
				var time = now.addDays(-day).toStr("yyyy-MM-dd");
				await this.$get(
						`~/api/order/sum_group?field=price_count&groupby=type&create_time_min= 00:00:00&create_time_max= 23:59:59`,
						null,
						(json) => {
							if (json.result) {
								json.result.list.map((o) => {
									list.push({
										time,
										value: o.sum,
										name: o.type,
									})
								});
							}
						}
				);
			},*/

			// 获取当天销售额
			url_orderDayPrice() {
				var date = new Date();
				// 获取当前日期
				var time = date.toStr("yyyy-MM-dd");
				var create_time_min = time + " 00:00:00";
				var create_time_max = time + " 23:59:59";
				var ret = "~/api/order/sum?field=price_count&create_time_min=" + create_time_min + "&create_time_max=" +
						create_time_max;
				return ret;
			},
				getUserInfo(){
					let userGroup = window.localStorage.getItem('user_group');
						if(userGroup){
						let _userGroup =JSON.parse(userGroup);
						let _info = JSON.parse(_userGroup.value);
						this.isAdmin = _info["user_group"] == "管理员" ? true : false;
					}
			}

		},
		computed:{
			recognitionHeight(){
				if(this.recognitionType === "face"){
					return "1070px"
				}else{
					return "1180px"
				}
			},
			recognitionUrl(){
					if(this.recognitionType === "face"){
					return "https://www.sk-ai.com/Experience/face-compare"
				}else{
					return "https://www.sk-ai.com/Experience/recognition?type="
				}
			}
		}
	};
</script>

<style scoped="scoped">
	.chart {
		display: block;
		width: 100%;
		height: 400px;
		padding: 1rem;
		position: relative;
	}

	.el-col {
		padding: 0.5rem;
	}

	.card {
		overflow: hidden;
	}
	
	.iframe_box ,.iframe_box_change{
		width: 100%;
		height: 1180px;
		position: relative;
		margin-top: 25px;
	}
	.iframe_box_change{
		height: 580px;
		padding-top: 50px;
	}
.iframe_box	.iframe_box_content, .iframe_box_change .iframe_box_content{
	width: 100%;
	height: 100%;
}
.iframe_box_top{
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100px;
	font-size: 25px;
	line-height: 100px;
	background: #fff;
	z-index: 99999999;
	padding-left: 50px;
}
</style>
