<template>
	<div class="list_address">
		<div class="address_card" v-for="(o,i) in list" :key="i">
			<div class="address_info">
				<div class="address_top">
					<div class="address_content">
						<div class="name">
							<span>{{o[vm.name]}}</span>
						</div>
						<div class="tag">
							<span class="tag_default"
								:class="{ 'show': o[vm.default] ,'hide': !o[vm.default] }">默认</span>
						</div>
						<div class="phone">
							<span>{{ o[vm.phone] }}</span>
						</div>
					</div>
					<div class="btn_show">
						<button @click="more_show(o,i)">
							{{ o[vm.display_btn] ? "折叠" : "展开" }}
						</button>
					</div>
				</div>
				<div class="address_bottom">
					<span>
						{{o[vm.address]}}
					</span>
				</div>
			</div>
			<div class="bar_btn" v-if="o[vm.display_btn]">
				<button @click="choose_default(o,i)" v-if="$check_action('/address/list','set')">
					<span>设为默认</span>
				</button>
				<button @click="del_address(o,i)" v-if="$check_action('/address/list','del')">
					<span>删除</span>
				</button>
				<button @click="$router.push('./address_edit?address_id='+o[vm.address_id])"
					v-if="$check_action('/address/list','set')">
					<span>编辑</span>
				</button>
			</div>
		</div>
		<div class="btn_address" @click="$router.push('./address_edit')" v-if="$check_action('/address/list','add')">+
			新建收货地址
		</div>
	</div>
</template>

<script>
	export default {
		name: "list_address",
		props: {
			list: {
				type: Array,
				default: function() {
					return [{
						name: "",
						phone: "",
						address: "",
						user_id: 0,
						address_id: 0,
						default: 0,
						display_btn: "display_btn"
					}];
				}
			},
			vm: {
				type: Object,
				default: function() {
					return {
						name: "name",
						phone: "phone",
						address: "address",
						user_id: "user_id",
						address_id: "address_id",
						default: "default",
						display_btn: "display_btn"
					}
				}
			}
		},
		data() {
			return {}
		},
		methods: {
			// 选择默认地址
			async choose_default(obj,i) {
				var _this = this;

				var user_id = obj.user_id;
				var address_id = obj.address_id;

				var res = await this.$post(`~/api/address/set?user_id=${user_id}`, {
					default: 0
				})

				if (res.result) {
					this.list.map(o => o.default = 0);

					this.$post(`~/api/address/set?user_id=${user_id}&address_id=${address_id}`, {
						default: 1
					}, (json) => {
						if (json.result) {
							_this.list[i].default = 1;
							this.$toast("修改默认地址成功！");
						} else if (json.error) {
							console.log(json.error);
							this.$toast(json.error.message);
						}
					})
				} else if (res.error) {
					console.log(res.error);
					this.$toast(json.error.message);
				}
			},

			// 删除
			del_address(obj,i) {
				this.$get("~/api/address/del", {
					"address_id": obj.address_id
				}, (res) => {
					if (res.result) {
						this.list.splice(i,1);
						this.$toast("删除地址成功！");
					} else if (res.error) {
						console.log(res.error)
						this.$toast(json.error.message);
					}
				})
			},

			// 更多展示
			more_show(o,i) {
				o.display_btn = !o.display_btn;
				this.$set(this.list,i,o);
			}
		}
	}
</script>

<style>
	.list_address {}

	.list_address .address_card {
		display: flex;
		flex-direction: column;
		padding: 0.5rem 1rem;
	}

	.list_address .address_info {
		display: flex;
		flex-direction: column;
	}

	.list_address .address_top {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
	}

	.list_address .address_bottom {
		height: 2.5rem;
	}

	.list_address .address_content {
		flex: 1;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		flex-wrap: wrap;
	}

	.list_address .hide {
		display: none;
	}

	.list_address .show {
		display: block;
	}

	.list_address .tag_default {
		font-weight: 600;
		font-size: 0.75rem;
		margin: 1rem;
		padding: 0.25rem;
		color: #4CD964;
		border: #4CD964 1px solid;
		border-radius: 5px;
	}

	.list_address .btn_show {
		padding-left: 1rem;
		width: 20%;
		min-width: 5rem;
	}

	.list_address .btn_show {
		color: #15BAF8;
		border-radius: 5px;
	}

	.list_address .bar_btn {
		margin-top: .5rem;
		display: flex;
		justify-content: start;
		flex-wrap: wrap;
	}

	.list_address .bar_btn>button {
		flex: 1;
		min-width: 7rem;
		border: #dcdcdc 1px solid;
	}

	.btn_address:hover {
		cursor: pointer;
	}

	.btn_address {
		color: #fff;
		font-size: 0.9rem;
		background-color: #FA4E28;
		height: 2.2rem;
		line-height: 2.2rem;
		border-radius: 1rem;
		margin: 0 auto;
		text-align: center;
	}
</style>
