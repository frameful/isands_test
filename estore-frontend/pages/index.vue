<script setup lang="ts">

import { Tabs } from "~/components/ui/tabs";
import { useToast } from "~/components/ui/toast";


const { toast } = useToast();

const loading = ref(false);
const componentKey =  ref(0)

function forceRerender () {
  componentKey.value += 1;
}

async function onFileChanged($event: Event) {
  const target = $event.target as HTMLInputElement;
  if (target.files && target.files) {
    const file = target.files[0];
    const formData = new FormData();
    formData.append("file", file);
    try {
      loading.value = true;
      const res = await $fetch("http://localhost:8081/estore/api/upload",
          {
            method: "POST",
            body: formData,
          });
      toast({
        description: "Файл УСПЕШНО ЗАГРУЖЕН"
      });
      forceRerender();
    } catch (e: any) {
      toast({
        title: "Ошибка",
        description: e.message,
      })
    } finally {
      loading.value = false;
    }
  }
}

const tab = ref('employee');

</script>

<template>
  <div class="mx-auto flex items-center justify-center py-10 flex-col space-y-4 max-w-[1200px]" :key="componentKey">
    <div class="flex items-center justify-center space-x-2">
        <label for="csv">Загрузить данные из csv:</label>
        <input type="file" id="csv" name="csv" accept=".zip" @change="onFileChanged($event)" />
        <p v-if="loading">.csv файл загружается...</p>
    </div>
    <Tabs default-value="employee" class="" v-model="tab">
      <TabsList class="grid w-full grid-cols-5">
        <TabsTrigger value="employee">
          Сотрудники
        </TabsTrigger>
        <TabsTrigger value="electroItem">
          Электроприборы
        </TabsTrigger>
        <TabsTrigger value="shop">
          Магазины
        </TabsTrigger>
        <TabsTrigger value="purchase">
          Покупки
        </TabsTrigger>
        <TabsTrigger value="auxiliary">
          Справочники
        </TabsTrigger>
      </TabsList>
      <KeepAlive>
        <TabsContent value="employee" v-if="tab == 'employee'" forceMount>
          <Card>
            <CardHeader>
              <CardTitle>Сотрудники</CardTitle>
              <CardDescription>
                Список сотрудников.
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-2">
                <TableEmployee />
            </CardContent>
            <CardFooter>
            </CardFooter>
          </Card>
        </TabsContent>
      </KeepAlive>
      <KeepAlive>
        <TabsContent value="electroItem" v-if="tab == 'electroItem'" forceMount>
          <Card>
            <CardHeader>
              <CardTitle>Электроприборы</CardTitle>
              <CardDescription>
                Список электроприборов.
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-2">
              <TableElectroItem />
            </CardContent>
            <CardFooter>
            </CardFooter>
          </Card>
        </TabsContent>
      </KeepAlive>
      <KeepAlive>
        <TabsContent value="shop" v-if="tab == 'shop'" forceMount>
          <Card>
            <CardHeader>
              <CardTitle>Магазины</CardTitle>
              <CardDescription>
                Список магазинов.
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-2">
                <TableShop />
            </CardContent>
            <CardFooter></CardFooter>
          </Card>
        </TabsContent>
      </KeepAlive>
      <KeepAlive>
        <TabsContent value="purchase" v-if="tab == 'purchase'" forceMount>
          <Card>
            <CardHeader>
              <CardTitle>Покупки</CardTitle>
              <CardDescription>
                Список покупок.
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-2">
              <TablePurchase />
            </CardContent>
            <CardFooter></CardFooter>
          </Card>
        </TabsContent>
      </KeepAlive>
      <KeepAlive>
        <TabsContent value="auxiliary" v-if="tab == 'auxiliary'" forceMount>
          <Card>
            <CardHeader>
              <CardTitle>Справочники</CardTitle>
            </CardHeader>
            <CardContent class="space-y-2">
                <TableAuxiliary name="electro_type" />
                <TableAuxiliary name="position_type" />
                <TableAuxiliary name="purchase_type"/>
            </CardContent>
            <CardFooter></CardFooter>
          </Card>
        </TabsContent>
      </KeepAlive>
    </Tabs>
  </div>

</template>

<style scoped>

</style>