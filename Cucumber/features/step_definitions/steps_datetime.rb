When(/^The admin clicks on the refresh icon$/) do
	$seleniumUtilities.click(".glyphicon-refresh")
end

Then(/^The time will be updated$/) do
end

Then(/^A check mark will appear$/) do
	sleep(2)
    $seleniumUtilities.isElementPresent(:xpath, "//a[@id='updateTime']/div/span[2]").should be_truthy
end