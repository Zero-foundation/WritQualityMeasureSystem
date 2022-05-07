import asyncio
import datetime

import pyppeteer
import time

from pyppeteer import launch


async def main(start_year, start_month, start_day):
    browser = await launch({'headless': False, 'args': ['--no-sandbox'], })
    page = await browser.newPage()
    await page.setUserAgent(
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299')
    await page.setViewport({'width': 1440, 'height': 720})
    await page.goto("https://wenshu.court.gov.cn/")
    content = await page.content()
    time.sleep(30)
    # loginLink = await page.xpath('//*[@id="loginLi"]/a')
    # await loginLink[0].click()
    # time.sleep(3)
    # await page.reload()
    # await page.waitForNavigation()
    # time.sleep(2)
    # TODO:处理iframe 暂时采用手动输入账号密码登录的方式
    # time.sleep(60)

    # ----------------------------------------
    time.sleep(2)
    await page.reload()
    time.sleep(2)
    await page.reload()
    time.sleep(2)
    await page.waitForNavigation()
    navaflag = True
    f = open(r'/log.txt', 'a')
    for year in range(2018, 2022):
        if (year < start_year):
            continue
        for month in range(1, 13):
            if (year == start_year and month < start_month):
                continue
            if month == 1 or month == 3 or month == 5 or month == 7 or month == 8 or month == 10 or month == 12:
                day_num = 31
            elif month == 2:
                day_num = 29 if year % 4 == 0 else 28
            else:
                day_num = 30
            for day in range(1, day_num + 1):
                if (year == start_year and month == start_month and day < start_day):
                    continue
                date_str = str(year) + '-' + \
                           (str(month) if len(str(month)) == 2 else ('0' + str(month))) + '-' + \
                           (str(day) if len(str(day)) == 2 else ('0' + str(day)))
                format = '%Y-%m-%d %H:%M:%S'
                f.write("\n" + datetime.datetime.now().strftime(format))
                f.write(" starting getting files on " + date_str)
                f.flush()
                advanced_search = await page.xpath('//*[@class="advenced-search"]')
                print(advanced_search[0])

                await advanced_search[0].click()
                time.sleep(1)
                # inputWrapper= await page.xpath('//*[@class="inputWrapper clearfix"]')
                anyou = await(page.xpath('//*[@class="inputWrapper clearfix"]/div[2]'))
                # print(anyou)
                await anyou[0].click()
                # await page.waitForNavigation()
                time.sleep(1)
                xingshi_anyou = await page.xpath('//*[@class="treeItem ay"]/div[1]/ul[1]/li[2]/a')
                await xingshi_anyou[0].click()
                court_name = (await(page.xpath('//*[@class="inputWrapper clearfix"]/div[5]/div[1]/input')))[0]
                await page.evaluate('document.querySelector("#s2").value=""')
                await court_name.type("江苏")
                time.sleep(1)
                from_time = (await(page.xpath('//*[@class="inputWrapper clearfix"]/div[10]/div[1]/input')))[0]
                to_time = (await(page.xpath('//*[@class="inputWrapper clearfix"]/div[10]/div[1]/input')))[1]
                # TODO: 每天的数据爬取

                await page.evaluate('document.querySelector("input[id=cprqStart]").value=""')
                await from_time.type(date_str)

                time.sleep(1)
                await page.evaluate('document.querySelector("input[id=cprqEnd]").value=""')
                await to_time.type(date_str)
                time.sleep(3)
                # searchbtn = (await (page.xpath('//*[@class="searchbtn"]/a')))[0]

                selector = "#searchBtn"
                await page.hover(selector)
                time.sleep(1)
                await page.mouse.down({'button': 'left'})
                time.sleep(0.2)
                await page.mouse.up({'button': 'left'})
                # 点击搜索按钮
                # await searchbtn.click()
                if (navaflag):
                    await page.waitForNavigation()
                    navaflag = False

                # 重新点击一次搜索按钮
                # advanced_search = await page.xpath('//*[@class="advenced-search"]')
                # await advanced_search[0].click()
                # time.sleep(1)
                # await page.hover(selector)
                # time.sleep(1)
                # await page.mouse.down({'button': 'left'})
                # time.sleep(0.2)
                # await page.mouse.up({'button': 'left'})
                # # 点击搜索按钮
                # # await searchbtn.click()
                # await page.waitForNavigation()

                time.sleep(2)
                if len(await page.xpath('//*[@class="pageSizeSelect"]')) == 0:
                    continue
                page_size_selector = (await page.xpath('//*[@class="pageSizeSelect"]'))[0]
                await page_size_selector.click()
                # 15个/页 展示
                # option3 = (await page.xpath('//*[@class="pageSizeSelect"]/option'))[2]
                # await option3.click()
                await page.select('.pageSizeSelect', "15")
                if len(await page.xpath('//*[@class="left_7_3"]/a')) == 0:
                    continue

                document_count = (await page.xpath('//*[@class="fr con_right"]/span'))[0]
                src = await page.evaluate('document.querySelector(".fr.con_right span").textContent')
                document_count = int(src)
                document_count = 600 if document_count > 600 else document_count
                page_num = document_count // 15
                f.write(" " + str(document_count) + " " + str(page_num))
                while True:
                    # TODO: 下载
                    selector = "#AllSelect"
                    await page.hover(selector)
                    time.sleep(1)
                    await page.mouse.down({'button': 'left'})
                    time.sleep(0.2)
                    await page.mouse.up({'button': 'left'})

                    # await page.click('#AllSelect')
                    selector = ".AllDownload"
                    await page.hover(selector)
                    time.sleep(1)
                    await page.mouse.down({'button': 'left'})
                    time.sleep(0.2)
                    await page.mouse.up({'button': 'left'})

                    time.sleep(2)
                    if page_num <= 0:
                        break
                    # TODO: 翻页
                    page_btn_list = (await page.xpath('//*[@class="left_7_3"]/a'))
                    if (len(page_btn_list) == 0):
                        break
                    await page_btn_list[-1].click()
                    page_num -= 1
                    time.sleep(2)

                # await page_btn_list[-1].click()
                # while (' pageButton' in properties.keys()):
                #     time.sleep(2)
                #     # TODO: 下载
                #     selector = "#AllSelect"
                #     await page.hover(selector)
                #     time.sleep(1)
                #     await page.mouse.down({'button': 'left'})
                #     time.sleep(0.2)
                #     await page.mouse.up({'button': 'left'})
                #
                #     # await page.click('#AllSelect')
                #     selector = ".AllDownload"
                #     await page.hover(selector)
                #     time.sleep(1)
                #     await page.mouse.down({'button': 'left'})
                #     time.sleep(0.2)
                #     await page.mouse.up({'button': 'left'})
                #     time.sleep(2)
                #     # download = (await page.xpath('//*[@class="fr tool_All"]/a'))[0]
                #     # await download.click()
                #     await page_btn_list[-1].click()
                #     time.sleep(2)
                #     page_btn_list = (await page.xpath('//*[@class="left_7_3"]/a'))
                #     properties = await page_btn_list[-1].getProperties()
                #     # 切换到下一页
                time.sleep(2)
                # TODO: 下载

    time.sleep(1000)
    # -----------------------------------------------------------------------------------

    # frame = page.frames[0]
    # content=await frame.content()
    # print(content)
    # account_input = await frame.xpath('//*[@id="root"]/div/form/div[1]/div[1]/input')
    # await frame.type('.phone-number-input', '15651680316')
    # password_input = await frame.xpath('//*[@id="root"]/div/form/div[1]/div[2]/input')
    # login_button = await frame.xpath('//*[@id="root"]/div/form/div[1]/div[3]/span')
    # await account_input[0].type('15651680316')
    # time.sleep(1)
    # await frame.type('#password', 'Zhuweitxdy123456')
    # await password_input[0].type('Zhuweitxdy123456')
    # time.sleep(1)
    # await frame.click('.button button-primary')
    # await login_button[0].click()
    # time.sleep(100)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main(2021, 11, 5))
