<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','commodity_number') || $check_field('add','commodity_number') || $check_field('set','commodity_number')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="商品编号" prop="commodity_number">
					<el-input id="commodity_number" v-model="form['commodity_number']" placeholder="请输入商品编号"
							  v-if="user_group === '管理员' || (form['sales_registration_id'] && $check_field('set','commodity_number')) || (!form['sales_registration_id'] && $check_field('add','commodity_number'))" :disabled="disabledObj['commodity_number_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','commodity_number')">{{form['commodity_number']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','commodity_type') || $check_field('add','commodity_type') || $check_field('set','commodity_type')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="商品类型" prop="commodity_type">
					<el-input id="commodity_type" v-model="form['commodity_type']" placeholder="请输入商品类型"
							  v-if="user_group === '管理员' || (form['sales_registration_id'] && $check_field('set','commodity_type')) || (!form['sales_registration_id'] && $check_field('add','commodity_type'))" :disabled="disabledObj['commodity_type_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','commodity_type')">{{form['commodity_type']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','trade_name') || $check_field('add','trade_name') || $check_field('set','trade_name')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="商品名称" prop="trade_name">
					<el-input id="trade_name" v-model="form['trade_name']" placeholder="请输入商品名称"
							  v-if="user_group === '管理员' || (form['sales_registration_id'] && $check_field('set','trade_name')) || (!form['sales_registration_id'] && $check_field('add','trade_name'))" :disabled="disabledObj['trade_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','trade_name')">{{form['trade_name']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','sales_volumes') || $check_field('add','sales_volumes') || $check_field('set','sales_volumes')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="销售数量" prop="sales_volumes">
					<el-input-number id="sales_volumes" v-model.number="form['sales_volumes']"
						v-if="user_group === '管理员' || (form['sales_registration_id'] && $check_field('set','sales_volumes')) || (!form['sales_registration_id'] && $check_field('add','sales_volumes'))"></el-input-number>
					<div v-else-if="$check_field('get','sales_volumes')">{{form['sales_volumes']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','remarks') || $check_field('add','remarks') || $check_field('set','remarks')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="备注" prop="remarks">
					<el-input id="remarks" v-model="form['remarks']" placeholder="请输入备注"
							  v-if="user_group === '管理员' || (form['sales_registration_id'] && $check_field('set','remarks')) || (!form['sales_registration_id'] && $check_field('add','remarks'))" :disabled="disabledObj['remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','remarks')">{{form['remarks']}}</div>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8">
				<el-form-item>
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "sales_registration_id",
				url_add: "~/api/sales_registration/add?",
				url_set: "~/api/sales_registration/set?",
				url_get_obj: "~/api/sales_registration/get_obj?",
				url_upload: "~/api/sales_registration/upload?",

				query: {
					"sales_registration_id": 0,
				},

				form: {
					"commodity_number":'', // 商品编号
					"commodity_type":'', // 商品类型
					"trade_name":'', // 商品名称
					"sales_volumes":0, // 销售数量
					"remarks":'', // 备注
					"sales_registration_id": 0, // ID

				},
				disabledObj:{
					"commodity_number_isDisabled": false,
					"commodity_type_isDisabled": false,
					"trade_name_isDisabled": false,
					"remarks_isDisabled": false,
				},

			}
		},
		methods: {

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
				// 获取缓存数据附加
				form = $.db.get("form");
				$.push(this.form ,form);
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
						})
					})
				}
				$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){

			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/sales_registration/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/sales_registration/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/sales_registration/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/sales_registration/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/sales_registration/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
