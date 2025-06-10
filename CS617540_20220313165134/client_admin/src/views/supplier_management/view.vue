<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','business_name') || $check_field('add','business_name') || $check_field('set','business_name')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="商家名称" prop="business_name">
					<el-input id="business_name" v-model="form['business_name']" placeholder="请输入商家名称"
							  v-if="user_group === '管理员' || (form['supplier_management_id'] && $check_field('set','business_name')) || (!form['supplier_management_id'] && $check_field('add','business_name'))" :disabled="disabledObj['business_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','business_name')">{{form['business_name']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','contacts') || $check_field('add','contacts') || $check_field('set','contacts')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="联系人" prop="contacts">
					<el-input id="contacts" v-model="form['contacts']" placeholder="请输入联系人"
							  v-if="user_group === '管理员' || (form['supplier_management_id'] && $check_field('set','contacts')) || (!form['supplier_management_id'] && $check_field('add','contacts'))" :disabled="disabledObj['contacts_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','contacts')">{{form['contacts']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','contact_information') || $check_field('add','contact_information') || $check_field('set','contact_information')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="联系方式" prop="contact_information">
					<el-input id="contact_information" v-model="form['contact_information']" placeholder="请输入联系方式"
							  v-if="user_group === '管理员' || (form['supplier_management_id'] && $check_field('set','contact_information')) || (!form['supplier_management_id'] && $check_field('add','contact_information'))" :disabled="disabledObj['contact_information_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','contact_information')">{{form['contact_information']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','remarks') || $check_field('add','remarks') || $check_field('set','remarks')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="备注" prop="remarks">
					<el-input id="remarks" v-model="form['remarks']" placeholder="请输入备注"
							  v-if="user_group === '管理员' || (form['supplier_management_id'] && $check_field('set','remarks')) || (!form['supplier_management_id'] && $check_field('add','remarks'))" :disabled="disabledObj['remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','remarks')">{{form['remarks']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','type_of_supply') || $check_field('add','type_of_supply') || $check_field('set','type_of_supply')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="供货类型" prop="type_of_supply">
					<el-input type="textarea" id="type_of_supply" v-model="form['type_of_supply']" placeholder="请输入供货类型"
						v-if="user_group === '管理员' || (form['supplier_management_id'] && $check_field('set','type_of_supply')) || (!form['supplier_management_id'] && $check_field('add','type_of_supply'))" :disabled="disabledObj['type_of_supply_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','type_of_supply')">{{form['type_of_supply']}}</div>
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
				field: "supplier_management_id",
				url_add: "~/api/supplier_management/add?",
				url_set: "~/api/supplier_management/set?",
				url_get_obj: "~/api/supplier_management/get_obj?",
				url_upload: "~/api/supplier_management/upload?",

				query: {
					"supplier_management_id": 0,
				},

				form: {
					"business_name":'', // 商家名称
					"contacts":'', // 联系人
					"contact_information":'', // 联系方式
					"remarks":'', // 备注
					"type_of_supply":'', // 供货类型
					"supplier_management_id": 0, // ID

				},
				disabledObj:{
					"business_name_isDisabled": false,
					"contacts_isDisabled": false,
					"contact_information_isDisabled": false,
					"remarks_isDisabled": false,
					"type_of_supply_isDisabled": false,
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
					bl = this.$check_action('/supplier_management/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier_management/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier_management/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier_management/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/supplier_management/view','get');
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
